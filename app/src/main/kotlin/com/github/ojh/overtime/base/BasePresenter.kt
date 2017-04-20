package com.github.ojh.overtime.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference
import kotlin.LazyThreadSafetyMode.NONE

abstract class BasePresenter<V : BaseContract.View>
    : BaseContract.Presenter<V> {

    protected val compositeDisposable: CompositeDisposable by lazy(NONE) {
        CompositeDisposable()
    }

    private var viewRef: WeakReference<V>? = null

    final override fun getView(): V? = viewRef?.get()

    final override fun attachView(view: V) {
        viewRef = WeakReference(view)
    }

    final override fun detachView() {
        compositeDisposable.dispose()
        viewRef?.clear()
        viewRef = null
    }

    final override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}