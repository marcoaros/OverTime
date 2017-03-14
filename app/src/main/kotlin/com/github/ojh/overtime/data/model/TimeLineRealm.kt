package com.github.ojh.overtime.data.model

import com.github.ojh.overtime.util.RealmUtil
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import org.parceler.Parcel
import java.util.*

@Parcel(value = Parcel.Serialization.BEAN, analyze = arrayOf(TimeLine::class))
@RealmClass
open class TimeLineRealm : RealmObject() {

    @PrimaryKey
    open var id: Int? = null

    open var date: Date? = null
    open var content: String? = null
    open var pay: Int? = null
    open var imgUri: String? = null

    fun toDto(): TimeLine {
        return TimeLine(id, date, content, pay, imgUri)
    }
}