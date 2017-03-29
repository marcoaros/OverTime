package com.github.ojh.overtime.data.local

import android.util.Log
import com.github.ojh.overtime.data.*
import com.github.ojh.overtime.util.RealmUtil
import com.github.ojh.overtime.util.extensions.getBetweenDates
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.Sort
import java.util.*

class LocalDataSource : DataSource {

    override fun getTimeLine(timeLineId: Int): Flowable<TimeLine> {
        val realm = Realm.getDefaultInstance()

        val timeLine = realm.where(TimeLineRealm::class.java)
                .equalTo("id", timeLineId)
                .findFirst()
                .toDto()

        return Flowable.just(timeLine)
    }

    override fun getTimeLines(filter: FilterType): Flowable<List<TimeLine>> {
        val realm = Realm.getDefaultInstance()
        val results = realm.where(TimeLineRealm::class.java)

        return when (filter) {

            is FilterEqualDate -> {
                val curDate = filter.date
                val datePair = curDate.getBetweenDates()

                Log.d("ddate pair", datePair.toString())


                Log.d("ddate realm", results
                        .between("date", datePair.first, datePair.second)
                        .findAll()
                        .sort("date", Sort.DESCENDING)
                        .toList()
                        .map(TimeLineRealm::toDto)
                        .toString())

                Flowable.just(
                        results
                                .between("date", datePair.first, datePair.second)
                                .findAll()
                                .sort("date", Sort.DESCENDING)
                                .toList()
                                .map(TimeLineRealm::toDto)
                )
            }

            is FilterDateDescending -> {
                Flowable.just(
                        results.findAll().sort("date", Sort.DESCENDING)
                                .toList()
                                .map(TimeLineRealm::toDto)
                )
            }

            is FilterDateAscending -> {
                Flowable.just(
                        results.findAll().sort("date", Sort.ASCENDING)
                                .toList()
                                .map(TimeLineRealm::toDto)
                )
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

        val timeLine = realm.where(TimeLineRealm::class.java)
                .equalTo("id", timeLineId)
                .findFirst()

        RealmUtil.delete(timeLine)
    }

    override fun getWrittenDates(): Flowable<List<Date>> {
        val realm = Realm.getDefaultInstance()

        val writtenDates = realm.where(TimeLineRealm::class.java).findAll()
                .toList()
                .map {
                    it.toDto().mDate
                }
                .filterNotNull()

        return Flowable.just(writtenDates)
    }
}