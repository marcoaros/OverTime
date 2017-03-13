package com.github.ojh.overtime.base

import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.github.ojh.overtime.OverTimeApplication
import com.github.ojh.overtime.di.AppComponent

abstract class BaseDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setComponent(OverTimeApplication.appComponent)
    }

    abstract fun setComponent(appComponent: AppComponent)
}