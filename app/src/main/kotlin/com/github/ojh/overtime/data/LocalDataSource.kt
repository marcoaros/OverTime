package com.github.ojh.overtime.data

import com.github.ojh.overtime.data.model.TimeLine
import com.github.ojh.overtime.util.RealmUtil
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.Sort
import java.util.*

class LocalDataSource : DataSource {

    override fun getTimeLines(): Flowable<List<TimeLine>> {
        val realm = Realm.getDefaultInstance()
        val results = realm.where(TimeLine::class.java).findAll().sort("date", Sort.DESCENDING)
        val list: List<TimeLine> = results.toList()
        return Flowable.just(list)
//        return RealmUtil.getRealm().map { it.where(TimeLine::class.java).findAll().sort("date", Sort.DESCENDING) }
    }

    override fun saveTimeLine(timeLine: TimeLine) {
        RealmUtil.save {
            timeLine.apply {
                id = timeLine.getNextId()
                date = Date()
            }
        }
    }

    override fun updateTimeLine(timeLine: TimeLine) {
        RealmUtil.save { timeLine }
    }
}