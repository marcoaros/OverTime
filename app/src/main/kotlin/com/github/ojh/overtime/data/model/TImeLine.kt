package com.github.ojh.overtime.data.model

import com.github.ojh.overtime.util.RealmUtil
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import org.parceler.Parcel
import java.util.*

@Parcel(value = Parcel.Serialization.BEAN, analyze = arrayOf(TimeLine::class))
data class TimeLine (
        var mId: Int? = null,
        var mDate: Date? = null,
        var mContent: String? = null,
        var mPay: Int? = null,
        var mImgUri: String? = null
) {

    companion object {
        const val KEY_TIMELINE = "key_timeline"
        const val KEY_TIMELINE_ID = "key_timeline_id"
    }

    fun getNextId(): Int {
        return RealmUtil.getNextId<TimeLineRealm>()
    }

    fun isValidAll(): Boolean = !mContent.isNullOrEmpty()

    fun toRealm(): TimeLineRealm {
        return TimeLineRealm().apply {
            id = mId
            date = mDate
            content = mContent
            pay = mPay
            imgUri = mImgUri
        }
    }
}