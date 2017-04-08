package com.github.ojh.overtime.main

import com.github.ojh.overtime.base.BaseContract
import com.github.ojh.overtime.data.TimeLine
import java.util.*

interface ListContract {
    interface View: BaseContract.View {
        fun navigateToDetail(view: android.view.View, timeLineId: Int)
        fun navigateToSetting(timeLineId: Int)
        fun scrollToPosition(position: Int)
    }

    interface Presenter<V: ListContract.View>: BaseContract.Presenter<V> {
        fun initDate(date: Date)
        fun getTimeLines()
        fun subscribeAddTimeLine(timeLine: TimeLine)
        fun subsribeUpdateTimeLine(timeLine: TimeLine)
        fun subscribeDeleteTimeLine(timeLineId: Int)
    }
}