package com.github.ojh.overtime.main.setting

import android.widget.CompoundButton
import com.github.ojh.overtime.base.BaseContract

interface SettingContract {
    interface View : BaseContract.View {
        fun setAlarmSwitch(isChecked: Boolean)
    }

    interface Presenter<V : View> : BaseContract.Presenter<V> {
        fun setOnCheckedChangeListener(view: CompoundButton, isChecked: Boolean)
        fun initSetting()
    }
}