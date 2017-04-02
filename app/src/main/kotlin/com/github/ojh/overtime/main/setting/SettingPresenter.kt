package com.github.ojh.overtime.main.setting

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.CompoundButton
import com.github.ojh.overtime.alarm.AlarmUtil
import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.util.PermissionUtil
import com.github.ojh.overtime.util.PermissionUtil.REQUEST_STORAGE
import com.github.ojh.overtime.util.PropertyUtil
import com.github.ojh.overtime.util.PropertyUtil.Companion.KEY_ALARM
import com.github.ojh.overtime.util.PropertyUtil.Companion.KEY_THEME
import javax.inject.Inject


class SettingPresenter<V : SettingContract.View> @Inject constructor(
        private val dataManager: DataManager,
        private val application: Application,
        private val propertyUtil: PropertyUtil

) : BasePresenter<V>(), SettingContract.Presenter<V> {

    override fun initSetting() {
        val isChecked = propertyUtil.getBoolean(KEY_ALARM, true)
        getView()?.setAlarmSwitch(isChecked)

        val theme = propertyUtil.getInt(KEY_THEME)
        getView()?.setThemeView(theme)

    }

    override fun setOnCheckedPushChangeListener(view: CompoundButton, isChecked: Boolean) {

        if (isChecked) {
            AlarmUtil.setOnceAlarm(application, AlarmUtil.DEFAULT_ALARM_HOUR, AlarmUtil.DEFAULT_ALARM_MINUTE)
        } else {
            AlarmUtil.cancelAlarm(application)
        }

        propertyUtil.setBoolean(KEY_ALARM, isChecked)

    }

    override fun setOnThemeSelectedListener(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        propertyUtil.setInt(KEY_THEME, position)
        getView()?.changeTheme(position)
    }

    override fun backupData() {
        compositeDisposable.add(
                dataManager.backUpData()
                        .subscribe {
                            getView()?.showToast(it)
                        }
        )
    }

    override fun checkStoragePermission(fragment: Fragment) {
        PermissionUtil.checkPermissionFromFragment(fragment, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_STORAGE)
    }

    override fun onRequestPermissionsResult(context: Context, requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getView()?.showBackUpDialog()
            } else {
                getView()?.showRationalDialog()
            }
        }
    }
}