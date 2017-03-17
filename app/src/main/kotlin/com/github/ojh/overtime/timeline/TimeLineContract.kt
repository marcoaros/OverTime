package com.github.ojh.overtime.timeline

import com.github.ojh.overtime.base.BaseContract
import com.github.ojh.overtime.data.model.TimeLine

interface TimeLineContract {
    interface View: BaseContract.View {
        fun navigateToWrite()
        fun navigateToDetail(timeLineId: Int)
        fun navigateToSetting(timeLineId: Int)
        fun scrollToPosition(position: Int)
    }
    interface Presenter<V : View>: BaseContract.Presenter<V> {
        fun initEventListener()
        fun getTimeLines()
        fun addTimeLine(timeLine: TimeLine, position: Int)
        fun updateTimeLine(timeLine: TimeLine)
        fun deleteTimeLine(timeLineId: Int)
    }
}