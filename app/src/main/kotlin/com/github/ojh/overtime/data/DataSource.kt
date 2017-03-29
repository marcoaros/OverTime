package com.github.ojh.overtime.data

import com.github.ojh.overtime.data.FilterType
import io.reactivex.Flowable

interface DataSource {
    fun getTimeLine(timeLineId: Int): Flowable<TimeLine>
    fun getTimeLines(filter: FilterType): Flowable<List<TimeLine>>
    fun saveTimeLine(timeLine: TimeLine)
    fun updateTimeLine(timeLine: TimeLine)
    fun deleteTimeLine(timeLineId: Int)
}