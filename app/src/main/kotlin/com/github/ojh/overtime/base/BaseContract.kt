package com.github.ojh.overtime.base

import android.widget.Toast
import io.reactivex.disposables.Disposable

interface BaseContract {

    interface View {
        fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT)
    }

    interface Presenter<V : View> {
        fun getView(): V?
        fun attachView(view: V)
        fun detachView()
        fun addDisposable(disposable: Disposable)
    }
}