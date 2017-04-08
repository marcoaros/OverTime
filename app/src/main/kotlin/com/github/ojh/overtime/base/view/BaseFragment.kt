package com.github.ojh.overtime.base.view

import android.os.Bundle
import android.support.v4.app.Fragment
import com.github.ojh.overtime.base.ActivityComponent
import com.github.ojh.overtime.base.BaseContract

abstract class BaseFragment<in C: ActivityComponent> : Fragment(), BaseContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityComponent = (activity as BaseActivity).getActivityComponent()

        @Suppress("UNCHECKED_CAST")
        setComponent(activityComponent as C)
    }

    abstract fun setComponent(activityComponent: C)
}