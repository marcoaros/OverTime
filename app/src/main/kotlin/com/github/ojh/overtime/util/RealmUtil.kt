package com.github.ojh.overtime.util

import android.app.Application
import android.os.Environment
import com.github.ojh.overtime.util.extensions.toFormatString
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.Disposables
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmConfiguration
import io.realm.RealmObject
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.LazyThreadSafetyMode.NONE

object RealmUtil {

    val realmConfiguration: RealmConfiguration by lazy(NONE) {
        RealmConfiguration.Builder()
                .name("overtime.realm")
                .schemaVersion(1)
                .build()
    }

    fun initRealm(application: Application) {
        Realm.init(application)
        Realm.setDefaultConfiguration(realmConfiguration)
    }

    fun getRealmInstance(): Realm {
        return Realm.getInstance(realmConfiguration)
    }

    fun save(realmObject: RealmObject) {
        val realm = Realm.getInstance(realmConfiguration)
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(realmObject)
        realm.commitTransaction()
        realm.close()
    }

    fun delete(realmObject: RealmObject) {
        val realm = Realm.getInstance(realmConfiguration)
        realm.beginTransaction()
        realmObject.deleteFromRealm()
        realm.commitTransaction()
        realm.close()
    }

    inline fun <reified T : RealmObject> getNextId(): Int {
        val realm = Realm.getInstance(realmConfiguration)
        val nextId = (realm.where(T::class.java).max("id")?.toInt() ?: 0) + 1
        realm.close()
        return nextId
    }

    fun getRealm(): Flowable<Realm> {
        return Flowable.create({ emitter ->
            val observableRealm = Realm.getDefaultInstance()

            val listener = RealmChangeListener<Realm> { _realm ->
                emitter.onNext(_realm)
            }

            emitter.setDisposable(Disposables.fromRunnable {
                observableRealm.removeChangeListener(listener)
                observableRealm.close()
            })

            observableRealm.addChangeListener(listener)
            emitter.onNext(observableRealm)
        }, BackpressureStrategy.LATEST)

    }

    fun backup(): String {

        val realm = Realm.getInstance(realmConfiguration)

        val externalPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val fileName = "overtime_${Date().toFormatString("yyyyMMdd_HHmmss")}.realm"

        var resultMessage = "download폴더에 $fileName 파일을 저장하였습니다"

        try {
            val realmBackUpFile = File(externalPath, fileName)
            realmBackUpFile.delete()
            realm.writeCopyTo(realmBackUpFile)

        } catch (e: IOException) {
            e.printStackTrace()
            resultMessage = "파일저장 실패"
        }

        realm.close()

        return resultMessage
    }

    fun restore(internalFilePath: String, externalFilePath: String): String {

        val externalFile = File(externalFilePath)
        val internalFile = File(internalFilePath)

        Realm.deleteRealm(realmConfiguration)
        externalFile.copyTo(internalFile, true)

        return "파일이 복구되었습니다."
    }
}