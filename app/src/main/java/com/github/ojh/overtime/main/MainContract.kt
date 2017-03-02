package com.github.ojh.overtime.main

import com.github.ojh.overtime.base.BaseContract

/**
 * Created by OhJaeHwan on 2017-02-28.
 */
interface MainContract {
    interface View: BaseContract.View {
        fun showToast(message: String)
    }
    interface Presenter<V : View>: BaseContract.Presenter<V> {
        fun clickFabWrite()
    }
}