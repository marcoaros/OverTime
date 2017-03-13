package com.github.ojh.overtime.timeline.dialog

import com.github.ojh.overtime.base.BaseContract

interface TimeLineSettingDialogContract {
    interface View : BaseContract.View {
        fun dismissDialog()
    }
    interface Presenter<V: View> : BaseContract.Presenter<V> {
        fun clickUpdate()
        fun clickDelete()
        fun clickCancel()
    }
}