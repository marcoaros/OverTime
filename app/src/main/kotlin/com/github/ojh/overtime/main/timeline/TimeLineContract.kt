package com.github.ojh.overtime.main.timeline

import com.github.ojh.overtime.base.BaseContract
import com.github.ojh.overtime.data.TimeLine
import com.github.ojh.overtime.data.FilterDateDescending
import com.github.ojh.overtime.data.FilterType

interface TimeLineContract {
    interface View: BaseContract.View {
        fun navigateToDetail(view: android.view.View, timeLineId: Int)
        fun navigateToSetting(timeLineId: Int)
        fun scrollToPosition(position: Int)
    }
    interface Presenter<V : View>: BaseContract.Presenter<V> {
        fun initEventListener()
        fun getFilterType(): FilterType
        fun getItemCount(): Int
        fun getTimeLines(filter: FilterType = FilterDateDescending())
        fun addTimeLine(timeLine: TimeLine)
        fun updateTimeLine(timeLine: TimeLine)
        fun deleteTimeLine(timeLineId: Int)
    }
}