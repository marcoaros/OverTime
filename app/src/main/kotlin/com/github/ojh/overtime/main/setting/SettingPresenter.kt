package com.github.ojh.overtime.main.setting

import android.app.Application
import android.widget.CompoundButton
import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.alarm.AlarmUtil
import com.github.ojh.overtime.util.PropertyUtil
import com.github.ojh.overtime.util.PropertyUtil.Companion.KEY_ALARM
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SettingPresenter<V : SettingContract.View> @Inject constructor(
        private val application: Application,
        private val propertyUtil: PropertyUtil,
        dataManager: DataManager,
        compositeDisposable: CompositeDisposable

) : BasePresenter<V>(dataManager, compositeDisposable), SettingContract.Presenter<V> {

    override fun initSetting() {
        val isChecked = propertyUtil.getBoolean(KEY_ALARM, true)
        getView()?.setAlarmSwitch(isChecked)
    }

    override fun setOnCheckedChangeListener(view: CompoundButton, isChecked: Boolean) {

        if (isChecked) {
            AlarmUtil.setOnceAlarm(application, AlarmUtil.DEFAULT_ALARM_HOUR, AlarmUtil.DEFAULT_ALARM_MINUTE)
        } else {
            AlarmUtil.cancelAlarm(application)
        }

        propertyUtil.setBoolean(KEY_ALARM, isChecked)

    }
}