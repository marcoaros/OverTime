package com.github.ojh.overtime.util

import io.realm.Realm
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