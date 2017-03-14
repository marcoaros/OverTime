package com.github.ojh.overtime.data

import com.github.ojh.overtime.data.model.TimeLine
import io.reactivex.Flowable

interface DataSource {
    fun getTimeLine(timeLineId: Int): Flowable<TimeLine>
    fun getTimeLines(): Flowable<List<TimeLine>>
    fun saveTimeLine(timeLine: TimeLine)
    fun updateTimeLine(timeLine: TimeLine)
    fun deleteTimeLine(timeLineId: Int)
}