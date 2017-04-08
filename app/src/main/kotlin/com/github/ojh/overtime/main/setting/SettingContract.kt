package com.github.ojh.overtime.main.setting

import android.content.Context
import android.support.v4.app.Fragment
import android.widget.AdapterView
import android.widget.CompoundButton
import com.github.ojh.overtime.base.BaseContract

interface SettingContract {
    interface View : BaseContract.View {
        fun setAlarmSwitch(isChecked: Boolean)
        fun setThemeSpinner(theme: Int)
        fun changeTheme(theme: Int)
        fun showRationalDialog()
        fun showToast(message: String)
        fun showRestoreDialog(pathList: List<String>)
        fun showProgress()
        fun dismissProgress()
        fun setLottieView(url: String)
    }

    interface Presenter<V : View> : BaseContract.Presenter<V> {
        fun changeAlarm(view: CompoundButton, isChecked: Boolean)
        fun selectTheme(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long)
        fun initSetting()
        fun loadBackUpFilePath()
        fun checkStoragePermission(fragment: Fragment, requestCode: Int)
        fun onRequestPermissionsResult(context: Context, requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
        fun backupData()
    }
}