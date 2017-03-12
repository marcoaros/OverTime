package com.github.ojh.overtime.timeline

import com.github.ojh.overtime.base.BaseContract
import com.github.ojh.overtime.data.model.TimeLine

interface TimeLineContract {
    interface View: BaseContract.View {
        fun showWriteDialog()
    }
    interface Presenter<V : View>: BaseContract.Presenter<V> {
        fun clickFabWrite()
        fun getTimeLines()
        fun addTimeLine(timeLine: TimeLine, position: Int)
        fun updateTimeLine(timeLine: TimeLine)
    }
}