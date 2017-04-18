package com.github.ojh.overtime.base.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.ojh.overtime.base.ActivityComponent
import com.github.ojh.overtime.base.AppComponent
import com.github.ojh.overtime.base.BaseContract
import com.github.ojh.overtime.base.OverTimeApplication
import com.github.ojh.overtime.util.PropertyUtil
import com.github.ojh.overtime.util.PropertyUtil.Companion.KEY_THEME
import com.github.ojh.overtime.util.theme.ThemeUtil

abstract class BaseActivity : AppCompatActivity(), BaseContract.View {

    private lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val theme = PropertyUtil(OverTimeApplication.application).getInt(KEY_THEME)
        ThemeUtil.onActivityCreateSetTheme(this, theme)
        activityComponent = setComponent(OverTimeApplication.appComponent)
    }

    abstract fun setComponent(appComponent: AppComponent): ActivityComponent

    fun getActivityComponent() = activityComponent
}