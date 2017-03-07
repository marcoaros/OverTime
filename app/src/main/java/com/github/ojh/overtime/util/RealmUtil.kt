package com.github.ojh.overtime.util

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmObject

/**
 * Created by ohjaehwan on 2017. 3. 7..
 */
object RealmUtil {
    fun execute(action: () -> RealmObject) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val realmData = action.invoke()
        realm.copyToRealmOrUpdate(realmData)
        realm.commitTransaction()
        realm.close()
    }
}