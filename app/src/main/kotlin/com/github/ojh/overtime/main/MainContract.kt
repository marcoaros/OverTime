package com.github.ojh.overtime.main

import com.github.ojh.overtime.base.BaseContract

interface MainContract {
    interface View: BaseContract.View {
        fun click()
    }

    interface Presenter<V: MainContract.View>: BaseContract.Presenter<V> {
        fun click()
    }
}