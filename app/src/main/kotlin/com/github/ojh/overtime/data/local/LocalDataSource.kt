package com.github.ojh.overtime.data.local

import com.github.ojh.overtime.data.*
import com.github.ojh.overtime.util.RealmUtil
import com.github.ojh.overtime.util.extensions.getBetweenDates
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.realm.Sort
import java.util.*

class LocalDataSource : DataSource {

    override fun getTimeLine(timeLineId: Int): Flowable<TimeLine> {

        return Flowable.create(
                {
                    val realm = RealmUtil.getRealmInstance()

                    val timeLine = realm.where(TimeLineRealm::class.java)
                            .equalTo("id", timeLineId)
                            .findFirst()
                            .toDto()

                    realm.close()

                    it.onNext(timeLine)
                    it.onComplete()
                }
                , BackpressureStrategy.LATEST
        )
    }

    override fun getTimeLines(filter: FilterType): Flowable<List<TimeLine>> {

        return Flowable.create(
                {
                    val realm = RealmUtil.getRealmInstance()
                    val results = realm.where(TimeLineRealm::class.java)

                    when (filter) {
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
                            it.onNext(timeLines)
                        }

                        is FilterDateDescending -> {
                            val timeLines = results
                                    .findAll()
                                    .sort("date", Sort.DESCENDING)
                                    .toList()
                                    .map(TimeLineRealm::toDto)

                            realm.close()
                            it.onNext(timeLines)
                        }

                        is FilterDateAscending -> {
                            val timeLines = results
                                    .findAll()
                                    .sort("date", Sort.ASCENDING)
                                    .toList()
                                    .map(TimeLineRealm::toDto)

                            realm.close()
                            it.onNext(timeLines)
                        }
                    }

                    it.onComplete()
                }
                , BackpressureStrategy.LATEST
        )
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
        return Flowable.create(
                {
                    val realm = RealmUtil.getRealmInstance()

                    val writtenDates = realm.where(TimeLineRealm::class.java).findAll()
                            .toList()
                            .map {
                                it.toDto().mDate
                            }
                            .filterNotNull()

                    realm.close()

                    it.onNext(writtenDates)
                    it.onComplete()

                }
                , BackpressureStrategy.LATEST
        )
    }

    override fun backUpData(): Flowable<String> {
        return Flowable.create(
                {
                    val resultString = RealmUtil.backup()
                    it.onNext(resultString)
                    it.onComplete()
                }
                , BackpressureStrategy.LATEST
        )
    }

    override fun restoreData(internalFilePath: String, externalFilePath: String): Flowable<String> {
        return Flowable.create(
                {
                    val resultString = RealmUtil.restore(internalFilePath, externalFilePath)
                    it.onNext(resultString)
                    it.onComplete()
                }
                , BackpressureStrategy.LATEST
        )
    }

}