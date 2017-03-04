package com.github.ojh.overtime.base

import com.github.ojh.overtime.data.DataManager
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * Created by ohjaehwan on 2017. 2. 28..
 */
open class BasePresenter<V : BaseContract.View> @Inject constructor(
        protected val dataManager: DataManager,
        protected val compositeDisposable: CompositeDisposable

): BaseContract.Presenter<V> {

    private var viewRef: WeakReference<V>? = null

    override fun getView(): V = viewRef?.get()!!

    override fun attachView(view: V) {
        viewRef = WeakReference(view)
    }

    override fun detachView() {
        compositeDisposable.dispose()
        viewRef?.clear()
        viewRef = null
    }

}