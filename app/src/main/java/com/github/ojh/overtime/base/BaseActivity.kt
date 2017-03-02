package com.github.ojh.overtime.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.ojh.overtime.OverTimeApplication
import com.github.ojh.overtime.di.AppComponent

/**
 * Created by ohjaehwan on 2017. 2. 28..
 */
abstract class BaseActivity : AppCompatActivity(), BaseContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setComponent(OverTimeApplication.appComponent)
    }

    abstract fun setComponent(appComponent: AppComponent)
}