package com.github.ojh.overtime.util

import android.app.Application
import android.os.Environment
import com.github.ojh.overtime.util.extensions.toFormatString
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
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

    fun getObservableRealm(): Observable<Realm> {
        return Observable.create {
            ObservableOnSubscribe<Realm> { emitter ->
                val observableRealm = Realm.getDefaultInstance()

                val listener = RealmChangeListener<Realm> { _realm ->
                    if (!emitter.isDisposed) {
                        emitter.onNext(_realm)
                    }
                }

                emitter.setDisposable(Disposables.fromRunnable {
                    observableRealm.removeChangeListener(listener)
                    observableRealm.close()
                })

                observableRealm.addChangeListener(listener)
                emitter.onNext(observableRealm)
            }

        }
    }


    fun backup(): String {

        val realm = Realm.getInstance(realmConfiguration)

        val exportRealmPATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val exportRealmFileName = "overtime_${Date().toFormatString("yyyyMMdd_HHmmss")}.realm"

        var resultMessage = "download폴더에 $exportRealmFileName 파일을 저장하였습니다"

        try {
            val exportRealmFile = File(exportRealmPATH, exportRealmFileName)
            exportRealmFile.delete()
            realm.writeCopyTo(exportRealmFile)
        } catch (e: IOException) {
            e.printStackTrace()
            resultMessage = "파일저장 실패"
        }

        realm.close()

        return resultMessage
    }

    fun restore(internalFilePath: String, exportFilePath: String): String {

        val exportFile = File(exportFilePath)
        val internalFile = File(internalFilePath)

        Realm.deleteRealm(realmConfiguration)
        exportFile.copyTo(internalFile, true)

        return "파일이 복구되었습니다. 앱을 다시 시작해주세요."
    }
}