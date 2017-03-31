package com.github.ojh.overtime.base.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.ojh.overtime.app.AppComponent
import com.github.ojh.overtime.app.OverTimeApplication
import com.github.ojh.overtime.base.ActivityComponent
import com.github.ojh.overtime.base.BaseContract

abstract class BaseActivity : AppCompatActivity(), BaseContract.View {

    private lateinit var activityComponent: ActivityComponent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = setComponent(OverTimeApplication.appComponent)
        setComponent(OverTimeApplication.appComponent)

    }

    abstract fun setComponent(appComponent: AppComponent): ActivityComponent

    fun getActivityComponent() = activityComponent
}