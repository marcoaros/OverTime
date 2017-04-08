package com.github.ojh.overtime.base.view

import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.AppComponent
import com.github.ojh.overtime.base.OverTimeApplication
import com.github.ojh.overtime.base.BaseContract

abstract class BaseDialogFragment : DialogFragment(), BaseContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
        setComponent(OverTimeApplication.appComponent)
    }

    abstract fun setComponent(appComponent: AppComponent)

}