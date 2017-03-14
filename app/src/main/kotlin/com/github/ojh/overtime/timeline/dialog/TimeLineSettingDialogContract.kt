package com.github.ojh.overtime.timeline.dialog

import com.github.ojh.overtime.base.BaseContract
import com.github.ojh.overtime.data.model.TimeLine

interface TimeLineSettingDialogContract {
    interface View : BaseContract.View {
        fun dismissDialog()
        fun navigateToWrite(timeLine: TimeLine)
    }
    interface Presenter<V: View> : BaseContract.Presenter<V> {
        fun init(timeLineId: Int)
        fun clickUpdate()
        fun clickDelete()
        fun clickCancel()
    }
}