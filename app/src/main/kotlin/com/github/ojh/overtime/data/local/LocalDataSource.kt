package com.github.ojh.overtime.data.local

import com.github.ojh.overtime.data.*
import com.github.ojh.overtime.util.RealmUtil
import com.github.ojh.overtime.util.extensions.getBetweenDates
import io.reactivex.Flowable
import io.realm.Sort
import java.util.*

class LocalDataSource : DataSource {

    override fun getTimeLine(timeLineId: Int): Flowable<TimeLine> {
        val realm = RealmUtil.getRealmInstance()

        val timeLine = realm.where(TimeLineRealm::class.java)
                .equalTo("id", timeLineId)
                .findFirst()
                .toDto()

        realm.close()
        return Flowable.just(timeLine)
    }

    override fun getTimeLines(filter: FilterType): Flowable<List<TimeLine>> {
        val realm = RealmUtil.getRealmInstance()
        val results = realm.where(TimeLineRealm::class.java)

        return when (filter) {

            is FilterEqualDate -> {
                val curDate = filter.date
                val datePair = curDate.getBetweenDates()

                val timeLines = results
                        .between("date", datePair.first, datePair.second)
                        .findAll()
                        .sort("date", Sort.DESCENDING)
                        .toList()
                        .map(TimeLineRealm::toDto)

                realm.close()
                Flowable.just(timeLines)
            }

            is FilterDateDescending -> {
                val timeLines = results
                        .findAll()
                        .sort("date", Sort.DESCENDING)
                        .toList()
                        .map(TimeLineRealm::toDto)

                realm.close()
                Flowable.just(timeLines)
            }

            is FilterDateAscending -> {
                val timeLines = results
                        .findAll()
                        .sort("date", Sort.ASCENDING)
                        .toList()
                        .map(TimeLineRealm::toDto)

                realm.close()
                Flowable.just(timeLines)
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
        val realm = RealmUtil.getRealmInstance()

        val timeLine = realm.where(TimeLineRealm::class.java)
                .equalTo("id", timeLineId)
                .findFirst()

        RealmUtil.delete(timeLine)
        realm.close()
    }

    override fun getWrittenDates(): Flowable<List<Date>> {
        val realm = RealmUtil.getRealmInstance()

        val writtenDates = realm.where(TimeLineRealm::class.java).findAll()
                .toList()
                .map {
                    it.toDto().mDate
                }
                .filterNotNull()

        realm.close()

        return Flowable.just(writtenDates)
    }

    override fun backUpData(): Flowable<String> {
        return Flowable.just(RealmUtil.backup())
    }

    override fun restoreData(internalFilePath: String, exportFilePath: String): Flowable<String> {
        return Flowable.just(RealmUtil.restore(internalFilePath, exportFilePath))
    }
}