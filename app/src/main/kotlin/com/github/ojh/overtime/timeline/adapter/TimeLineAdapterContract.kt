package com.github.ojh.overtime.timeline.adapter

import com.github.ojh.overtime.data.model.TimeLine
import com.github.ojh.overtime.util.ViewClickHandler

interface TimeLineAdapterContract {
    interface Model {
        fun setTimeLines(timeLines: List<TimeLine>)
        fun addTimeLine(timeLine: TimeLine, position: Int)
        fun updateTimeLine(timeLine: TimeLine)
        fun deleteTimeLine(timeLineId: Int)
        fun findTimeLineId(position: Int): Int?
    }

    interface View {
        fun setOnClickViewHolder(listener: ViewClickHandler)
        fun setOnLongClickViewHolder(listener: ViewClickHandler)
        fun refreshAll()
        fun refreshItemChanged(position: Int)
        fun refreshItemInserted(position: Int)
        fun refreshItemRemoved(position: Int)
    }
}