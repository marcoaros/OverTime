package com.github.ojh.overtime.data

import io.reactivex.Flowable

interface DataSource {
    fun getTimeLine(timeLineId: Int): Flowable<TimeLine>
    fun getTimeLines(filter: Int): Flowable<List<TimeLine>>
    fun saveTimeLine(timeLine: TimeLine)
    fun updateTimeLine(timeLine: TimeLine)
    fun deleteTimeLine(timeLineId: Int)
}