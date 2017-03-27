package com.github.ojh.overtime.base.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.ojh.overtime.app.AppComponent
import com.github.ojh.overtime.app.OverTimeApplication
import com.github.ojh.overtime.base.ActivityComponent
import com.github.ojh.overtime.base.BaseContract

abstract class BaseActivity<C: ActivityComponent> : AppCompatActivity(), BaseContract.View {

    private lateinit var component: C

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = setComponent(OverTimeApplication.appComponent)

    }

    abstract fun setComponent(appComponent: AppComponent): C

    fun getActivityComponent() = component
}