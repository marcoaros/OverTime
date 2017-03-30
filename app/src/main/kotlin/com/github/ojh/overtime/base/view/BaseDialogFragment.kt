package com.github.ojh.overtime.base.view

import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.github.ojh.overtime.app.AppComponent
import com.github.ojh.overtime.app.OverTimeApplication
import com.github.ojh.overtime.base.BaseContract

abstract class BaseDialogFragment : DialogFragment(), BaseContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setComponent(OverTimeApplication.appComponent)
    }

    abstract fun setComponent(appComponent: AppComponent)

}