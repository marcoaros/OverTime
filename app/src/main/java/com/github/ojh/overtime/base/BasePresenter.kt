package com.github.ojh.overtime.base

import java.lang.ref.WeakReference

/**
 * Created by ohjaehwan on 2017. 2. 28..
 */
abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {
    private var viewRef: WeakReference<V>? = null

    override fun getView(): V = viewRef?.get() as V

    override fun attachView(view: V) {
        viewRef = WeakReference(view)
    }

    override fun detachView() {
        viewRef?.clear()
        viewRef = null
    }
}