package com.github.ojh.overtime.base

import com.github.ojh.overtime.data.DataSource
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * Created by ohjaehwan on 2017. 2. 28..
 */
open class BasePresenter<V : BaseContract.View> @Inject constructor(
        protected val dataSource: DataSource,
        protected val compositeDisposable: CompositeDisposable

): BaseContract.Presenter<V> {
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