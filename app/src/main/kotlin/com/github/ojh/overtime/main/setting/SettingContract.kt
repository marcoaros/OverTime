package com.github.ojh.overtime.main.setting

import android.widget.AdapterView
import android.widget.CompoundButton
import com.github.ojh.overtime.base.BaseContract

interface SettingContract {
    interface View : BaseContract.View {
        fun setAlarmSwitch(isChecked: Boolean)
        fun setThemeView(theme: Int)
        fun changeTheme(theme: Int)
    }

    interface Presenter<V : View> : BaseContract.Presenter<V> {
        fun setOnCheckedPushChangeListener(view: CompoundButton, isChecked: Boolean)
        fun setOnThemeSelectedListener(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long)
        fun initSetting()
    }
}