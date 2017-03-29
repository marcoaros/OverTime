package com.github.ojh.overtime.main

import com.github.ojh.overtime.base.BaseContract
import java.util.*

interface ListContract {
    interface View: BaseContract.View {
        fun navigateToDetail(view: android.view.View, timeLineId: Int)
    }

    interface Presenter<V: ListContract.View>: BaseContract.Presenter<V> {
        fun initDate(date: Date)
        fun initEventListener()
        fun getTimeLines()
    }
}