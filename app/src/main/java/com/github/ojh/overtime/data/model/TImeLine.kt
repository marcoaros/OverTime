package com.github.ojh.overtime.data.model

import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

/**
 * Created by ohjaehwan on 2017. 3. 2..
 */
@RealmClass
open class TimeLine : RealmObject() {

    @PrimaryKey
    open var id: Int = 0

    open var date: Date? = null
    open var content: String? = null
    open var pay: Int = 0

    fun getNextId(): Int {
        val realm = Realm.getDefaultInstance()
        val nextId = (realm.where(TimeLine::class.java).max("id")?.toInt() ?: 0) + 1
        realm.close()
        return nextId
    }

    fun isValidAll(): Boolean = !content.isNullOrEmpty()
}