package com.github.ojh.overtime.base.view

import android.os.Bundle
import android.support.v4.app.Fragment
import com.github.ojh.overtime.base.ActivityComponent
import com.github.ojh.overtime.base.BaseContract
import com.github.ojh.overtime.util.extensions.toast

abstract class BaseFragment<in C: ActivityComponent> : Fragment(), BaseContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityComponent = (activity as BaseActivity).getActivityComponent()

        @Suppress("UNCHECKED_CAST")
        setComponent(activityComponent as C)
    }

    abstract fun setComponent(activityComponent: C)

    final override fun showToast(message: String, duration: Int) {
        toast(message, duration)
    }
}