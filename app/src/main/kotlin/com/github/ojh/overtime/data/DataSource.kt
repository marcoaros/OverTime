package com.github.ojh.overtime.data

import io.reactivex.Flowable
import java.util.*

interface DataSource {
    fun getTimeLine(timeLineId: Int): Flowable<TimeLine>
    fun getTimeLines(filter: FilterType): Flowable<List<TimeLine>>
    fun saveTimeLine(timeLine: TimeLine)
    fun updateTimeLine(timeLine: TimeLine)
    fun deleteTimeLine(timeLineId: Int)
    fun getWrittenDates(): Flowable<List<Date>>
}