package com.github.ojh.overtime.data.local

import com.github.ojh.overtime.data.DataSource
import com.github.ojh.overtime.data.TimeLine
import com.github.ojh.overtime.util.RealmUtil
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.Sort
import java.util.*

class LocalDataSource : DataSource {

    override fun getTimeLine(timeLineId: Int): Flowable<TimeLine> {
        val realm = Realm.getDefaultInstance()
        val timeLine = realm.where(TimeLineRealm::class.java).equalTo("id", timeLineId).findFirst()
        return Flowable.just(timeLine.toDto())
    }

    override fun getTimeLines(filter: Int): Flowable<List<TimeLine>> {
        val realm = Realm.getDefaultInstance()
        val results = realm.where(TimeLineRealm::class.java).findAll()

        when (filter) {
            1 -> {
                return Flowable.just(results.sort("date", Sort.ASCENDING).toList().map { it.toDto() })
            }
            else -> {
                return Flowable.just(results.sort("date", Sort.DESCENDING).toList().map { it.toDto() })
            }
        }
    }

    override fun saveTimeLine(timeLine: TimeLine) {
        RealmUtil.save(
                timeLine.apply {
                    mId = timeLine.getNextId()
                    mDate = Date()
                }.toRealm()
        )
    }

    override fun updateTimeLine(timeLine: TimeLine) {
        RealmUtil.save(timeLine.toRealm())
    }

    override fun deleteTimeLine(timeLineId: Int) {
        val realm = Realm.getDefaultInstance()
        val timeLine = realm.where(TimeLineRealm::class.java).equalTo("id", timeLineId).findFirst()
        RealmUtil.delete(timeLine)
    }
}