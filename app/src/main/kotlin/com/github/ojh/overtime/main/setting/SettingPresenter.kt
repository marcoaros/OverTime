package com.github.ojh.overtime.main.setting

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.support.v4.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.CompoundButton
import com.github.ojh.overtime.alarm.AlarmUtil
import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.util.PermissionUtil
import com.github.ojh.overtime.util.PropertyUtil
import com.github.ojh.overtime.util.PropertyUtil.Companion.KEY_ALARM
import com.github.ojh.overtime.util.PropertyUtil.Companion.KEY_THEME
import com.github.ojh.overtime.util.firebase.FirebaseUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SettingPresenter<V : SettingContract.View> @Inject constructor(
        private val dataManager: DataManager,
        private val application: Application,
        private val propertyUtil: PropertyUtil

) : BasePresenter<V>(), SettingContract.Presenter<V> {

    companion object {
        const val REQUEST_BACKUP = 100
        const val REQUEST_RESTORE = 200
    }

    override fun initSetting() {

        val lottieUrl = FirebaseUtil.getString("setting_url")
        getView()?.setLottieView(lottieUrl)

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
        getView()?.showProgress()

        compositeDisposable.add(
                dataManager.backUpData()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete {
                            getView()?.dismissProgress()
                        }
                        .subscribe {
                            getView()?.showToast(it)
                        }
        )
    }

    override fun checkStoragePermission(fragment: Fragment, requestCode: Int) {
        PermissionUtil.checkPermissionFromFragment(
                fragment,
                requestCode,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    override fun onRequestPermissionsResult(context: Context, requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_RESTORE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadBackUpFilePaths()
            } else {
                getView()?.showRationalDialog()
            }
        } else if (requestCode == REQUEST_BACKUP) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                backupData()
            } else {
                getView()?.showRationalDialog()
            }
        }
    }

    override fun loadBackUpFilePaths() {
        val exportRealmPATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        val backUpFileList = exportRealmPATH.listFiles()
                .filter {
                    it.name.contains(".realm")
                }
                .map {
                    it.path
                }

        if (backUpFileList.isEmpty()) {
            getView()?.showToast("백업을 할 파일이 없습니다. download 폴더에 .realm 파일을 넣어주세요")
            return
        }
        getView()?.showRestoreDialog(backUpFileList)
    }
}