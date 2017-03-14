package com.github.ojh.overtime.data

import com.github.ojh.overtime.data.model.TimeLine
import com.github.ojh.overtime.data.model.TimeLineRealm
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

    override fun getTimeLines(): Flowable<List<TimeLine>> {
        val realm = Realm.getDefaultInstance()
        val results = realm.where(TimeLineRealm::class.java).findAllSorted("date", Sort.DESCENDING)
        val list: List<TimeLine> = results.toList().map { it.toDto() }
        return Flowable.just(list)
//        return RealmUtil.getRealm().map { it.where(TimeLine::class.java).findAllSorted("date", Sort.DESCENDING) }
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