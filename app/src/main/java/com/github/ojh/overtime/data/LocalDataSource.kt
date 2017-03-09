package com.github.ojh.overtime.data

import com.github.ojh.overtime.data.model.TimeLine
import com.github.ojh.overtime.util.RealmUtil
import io.reactivex.Flowable
import io.realm.Realm

/**
 * Created by OhJaeHwan on 2017-02-28.
 */
class LocalDataSource : DataSource {

    override fun getTimeLines(): Flowable<List<TimeLine>> {
        val realm = Realm.getDefaultInstance()
        val results = realm.where(TimeLine::class.java).findAll()
        val list: List<TimeLine> = results.toList()
        return Flowable.just(list)

//        return RealmUtil.getRealm().map { it.where(TimeLine::class.java).findAll() }
    }

    override fun saveTimeLine(timeLine: TimeLine) {
        RealmUtil.execute {
            timeLine.apply {
                id = timeLine.getNextId()
            }
        }
    }
}