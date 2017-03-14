package com.github.ojh.overtime.timeline.list.adapter

import com.github.ojh.overtime.data.model.TimeLine

interface TimeLineAdapterContract {
    interface Model {
        fun setTimeLines(timeLines: List<TimeLine>)
        fun addTimeLine(timeLine: TimeLine, position: Int)
        fun updateTimeLine(timeLine: TimeLine)
        fun deleteTimeLine(timeLineId: Int)
    }
    interface View {
        fun refreshAll()
    }
}