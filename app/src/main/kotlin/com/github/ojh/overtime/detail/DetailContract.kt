package com.github.ojh.overtime.detail

import com.github.ojh.overtime.base.BaseContract
import com.github.ojh.overtime.data.TimeLine

interface DetailContract {
    interface View : BaseContract.View {
        fun initView(timeLine: TimeLine)
    }

    interface Presenter<V: View> : BaseContract.Presenter<V> {
        fun init(timeLineId: Int)
    }

}