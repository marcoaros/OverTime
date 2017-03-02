package com.github.ojh.overtime.base

/**
 * Created by ohjaehwan on 2017. 2. 28..
 */
interface BaseContract {
    interface View

    interface Presenter<V : View> {
        fun getView(): V
        fun attachView(view: V)
        fun detachView()
    }
}