package com.github.ojh.overtime.base

import com.github.ojh.overtime.data.DataManager
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

open class BasePresenter<V : BaseContract.View> (
        protected val dataManager: DataManager,
        protected val compositeDisposable: CompositeDisposable

): BaseContract.Presenter<V> {

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