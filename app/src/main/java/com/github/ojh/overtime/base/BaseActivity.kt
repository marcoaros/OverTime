package com.github.ojh.overtime.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by ohjaehwan on 2017. 2. 28..
 */
abstract class BaseActivity<V : BaseContract.View,
        out P : BaseContract.Presenter<V>,
        out C : BaseContract.Component<V, P>>
    : AppCompatActivity(), BaseContract.View {

    protected val presenter: P by lazy { component.presenter() }
    protected val component: C by lazy { createComponent() }
    protected abstract fun createComponent(): C

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this as V)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}