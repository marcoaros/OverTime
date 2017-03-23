package com.github.ojh.overtime.main.timeline

import com.github.ojh.overtime.base.BaseContract
import com.github.ojh.overtime.data.TimeLine

interface TimeLineContract {
    interface View: BaseContract.View {
        fun navigateToWrite()
        fun navigateToDetail(view: android.view.View, timeLineId: Int)
        fun navigateToSetting(timeLineId: Int)
        fun scrollToPosition(position: Int)
    }
    interface Presenter<V : View>: BaseContract.Presenter<V> {
        fun initEventListener()
        fun clickWrite()
        fun getTimeLines(filter: Int = 0)
        fun addTimeLine(timeLine: TimeLine)
        fun updateTimeLine(timeLine: TimeLine)
        fun deleteTimeLine(timeLineId: Int)
    }
}