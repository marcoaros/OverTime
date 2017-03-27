package com.github.ojh.overtime.edit

import com.github.ojh.overtime.base.BaseContract
import com.github.ojh.overtime.data.TimeLine

interface EditContract {
    interface View : BaseContract.View {
        fun dismissDialog()
        fun navigateToWrite(timeLine: TimeLine)
    }
    interface Presenter<V: View> : BaseContract.Presenter<V> {
        fun init(timeLineId: Int)
        fun updateTimeLine()
        fun deleteTimeLine()
        fun dismiss()
    }
}