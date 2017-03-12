package com.github.ojh.overtime.data

import com.github.ojh.overtime.data.model.TimeLine
import io.reactivex.Flowable
import javax.inject.Inject

class DataManager @Inject constructor(
        private val dataSource: DataSource
) {

    fun getTimeLineData(): Flowable<List<TimeLine>> {
        return dataSource.getTimeLines()
    }

    fun saveTimeLine(timeLine: TimeLine) {
        dataSource.saveTimeLine(timeLine)
    }

    fun updateTimeLine(timeLine: TimeLine) {
        dataSource.updateTimeLine(timeLine)
    }
}