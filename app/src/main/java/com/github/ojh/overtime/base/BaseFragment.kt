package com.github.ojh.overtime.base

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.View

/**
 * Created by ohjaehwan on 2017. 2. 28..
 */
abstract class BaseFragment<V : BaseContract.View,
        out P : BaseContract.Presenter<V>,
        out C : BaseContract.Component<V, P>>
    : AppCompatActivity(), BaseContract.View {

    protected val presenter: P by lazy { component.presenter() }
    protected val component: C by lazy { createComponent() }

    protected abstract fun createComponent(): C

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(name: String?, context: Context?, attrs: AttributeSet?): View {
        presenter.attachView(this as V)
        return super.onCreateView(name, context, attrs)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}