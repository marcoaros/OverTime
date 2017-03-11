package com.github.ojh.overtime.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.ojh.overtime.OverTimeApplication
import com.github.ojh.overtime.di.AppComponent

abstract class BaseActivity : AppCompatActivity(), BaseContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setComponent(OverTimeApplication.appComponent)
    }

    abstract fun setComponent(appComponent: AppComponent)
}