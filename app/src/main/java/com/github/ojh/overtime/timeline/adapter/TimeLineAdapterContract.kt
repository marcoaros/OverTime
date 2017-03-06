package com.github.ojh.overtime.timeline.adapter

import com.github.ojh.overtime.data.model.TimeLine

/**
 * Created by ohjaehwan on 2017. 3. 2..
 */
interface TimeLineAdapterContract {
    interface Model {
        fun setTimeLines(timeLines: List<TimeLine>)
    }
    interface View {
        fun refresh()
    }
}