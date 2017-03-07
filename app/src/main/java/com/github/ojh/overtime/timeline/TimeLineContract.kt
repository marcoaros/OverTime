package com.github.ojh.overtime.timeline

import com.github.ojh.overtime.base.BaseContract

/**
 * Created by OhJaeHwan on 2017-02-28.
 */
interface TimeLineContract {
    interface View: BaseContract.View {
        fun showWriteDialog()
    }
    interface Presenter<V : View>: BaseContract.Presenter<V> {
        fun clickFabWrite()
        fun getData()
    }
}