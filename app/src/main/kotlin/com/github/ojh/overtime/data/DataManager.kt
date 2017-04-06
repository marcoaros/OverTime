package com.github.ojh.overtime.data

import io.reactivex.Flowable
import java.util.*
import javax.inject.Inject

class DataManager @Inject constructor(
        private val dataSource: DataSource
) {

    fun getTimeLine(timeLineId: Int): Flowable<TimeLine> {
        return dataSource.getTimeLine(timeLineId)
    }

    fun getTimeLines(filter: FilterType): Flowable<List<TimeLine>> {
        return dataSource.getTimeLines(filter)
    }

    fun saveTimeLine(timeLine: TimeLine) {
        dataSource.saveTimeLine(timeLine)
    }

    fun updateTimeLine(timeLine: TimeLine) {
        dataSource.updateTimeLine(timeLine)
    }

    fun deleteTimeLine(timeLineId: Int) {
        dataSource.deleteTimeLine(timeLineId)
    }

    fun getWrittenDates(): Flowable<List<Date>> {
        return dataSource.getWrittenDates()
    }

    fun backUpData(): Flowable<String> {
        return dataSource.backUpData()
    }

    fun restoreData(internalFilePath:String, externalFilePath: String): Flowable<String> {
        return dataSource.restoreData(internalFilePath, externalFilePath)
    }
}