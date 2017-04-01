package com.github.ojh.overtime.base

import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference
import kotlin.LazyThreadSafetyMode.NONE

open class BasePresenter<V : BaseContract.View>
    : BaseContract.Presenter<V> {

    protected val compositeDisposable: CompositeDisposable by lazy(NONE) {
        CompositeDisposable()
    }

    private var viewRef: WeakReference<V>? = null

    override fun getView(): V? = viewRef?.get()

    override fun attachView(view: V) {
        viewRef = WeakReference(view)
    }

    override fun detachView() {
        compositeDisposable.dispose()
        viewRef?.clear()
        viewRef = null
    }

}