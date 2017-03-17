package com.github.ojh.overtime.setting

import com.github.ojh.overtime.base.BaseContract
import com.github.ojh.overtime.data.model.TimeLine

interface TimeLineSettingDialogContract {
    interface View : BaseContract.View {
        fun dismissDialog()
        fun navigateToWrite(timeLine: TimeLine)
    }
    interface Presenter<V: View> : BaseContract.Presenter<V> {
        fun init(timeLineId: Int)
        fun updateTiemLine()
        fun deleteTimeLine()
        fun dismiss()
    }
}