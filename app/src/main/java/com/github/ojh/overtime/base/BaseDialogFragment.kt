package com.github.ojh.overtime.base

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ojh.overtime.OverTimeApplication
import com.github.ojh.overtime.di.AppComponent

/**
 * Created by ohjaehwan on 2017. 3. 6..
 */
abstract class BaseDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setComponent(OverTimeApplication.appComponent)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    abstract fun setComponent(appComponent: AppComponent)
}