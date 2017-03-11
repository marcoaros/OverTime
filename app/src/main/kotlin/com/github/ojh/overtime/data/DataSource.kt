package com.github.ojh.overtime.data

import com.github.ojh.overtime.data.model.TimeLine
import io.reactivex.Flowable

interface DataSource {
    fun getTimeLines(): Flowable<List<TimeLine>>
    fun saveTimeLine(timeLine: TimeLine)
}