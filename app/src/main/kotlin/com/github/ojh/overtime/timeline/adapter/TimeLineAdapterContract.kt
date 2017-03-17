package com.github.ojh.overtime.timeline.adapter

import com.github.ojh.overtime.data.model.TimeLine

interface TimeLineAdapterContract {
    interface Model {
        fun setTimeLines(timeLines: List<TimeLine>)
        fun addTimeLine(timeLine: TimeLine, position: Int)
        fun updateTimeLine(timeLine: TimeLine)
        fun deleteTimeLine(timeLineId: Int)
        fun findTimeLineId(position: Int): Int?
    }

    interface View {
        fun setOnClickViewHolder(listener: (position: Int)->Unit)
        fun setOnLongClickViewHolder(listener: (position: Int)->Unit)
        fun refreshAll()
        fun refreshItemChanged(position: Int)
        fun refreshItemInserted(position: Int)
        fun refreshItemRemoved(position: Int)
    }
}