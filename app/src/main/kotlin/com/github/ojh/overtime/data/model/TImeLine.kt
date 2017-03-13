package com.github.ojh.overtime.data.model

import com.github.ojh.overtime.util.RealmUtil
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import org.parceler.Parcel
import java.util.*

@Parcel(value = Parcel.Serialization.BEAN, analyze = arrayOf(TimeLine::class))
@RealmClass
open class TimeLine : RealmObject() {


    companion object {
//        @Ignore
        const val KEY_TIMELINE = "key_timeline"
        const val KEY_TIMELINE_ID = "key_timeline_id"
    }

    @PrimaryKey
    open var id: Int? = null

    open var date: Date? = null
    open var content: String? = null
    open var pay: Int? = null
    open var imgUri: String? = null

    fun getNextId(): Int {
        return RealmUtil.getNextId<TimeLine>()
    }

    fun isValidAll(): Boolean = !content.isNullOrEmpty()
}