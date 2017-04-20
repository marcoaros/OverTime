package com.github.ojh.overtime.base

import android.widget.Toast

interface BaseContract {
    interface View {
        fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT)
    }

    interface Presenter<V : View> {
        fun getView(): V?
        fun attachView(view: V)
        fun detachView()
    }
}