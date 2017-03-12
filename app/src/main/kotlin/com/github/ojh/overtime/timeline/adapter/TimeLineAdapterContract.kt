package com.github.ojh.overtime.timeline.adapter

import com.github.ojh.overtime.data.model.TimeLine

interface TimeLineAdapterContract {
    interface Model {
        fun setTimeLines(timeLines: List<TimeLine>)
        fun addTimeLine(timeLine: TimeLine, position: Int)
        fun updateTimeLine(timeLine: TimeLine)
    }
    interface View {
        fun refreshAll()
    }
}