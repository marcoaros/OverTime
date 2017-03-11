package com.github.ojh.overtime.data.model

import com.github.ojh.overtime.util.RealmUtil
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class TimeLine : RealmObject() {

    @PrimaryKey
    open var id: Int = 0

    open var date: Date? = null
    open var content: String? = null
    open var pay: Int = 0
    open var imgUri: String? = null

    fun getNextId(): Int {
        return RealmUtil.getNextId<TimeLine>()
    }

    fun isValidAll(): Boolean = !content.isNullOrEmpty()
}