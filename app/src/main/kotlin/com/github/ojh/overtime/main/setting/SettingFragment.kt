package com.github.ojh.overtime.main.setting


import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.view.BaseFragment
import com.github.ojh.overtime.main.MainComponent
import com.github.ojh.overtime.main.setting.SettingPresenter.Companion.REQUEST_BACKUP
import com.github.ojh.overtime.main.setting.SettingPresenter.Companion.REQUEST_RESTORE
import com.github.ojh.overtime.main.setting.backup.RestoreDialogFragment
import com.github.ojh.overtime.util.PermissionUtil
import com.github.ojh.overtime.util.extensions.toast
import com.github.ojh.overtime.util.theme.ThemeUtil
import kotlinx.android.synthetic.main.fragment_setting.*
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE


/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : BaseFragment<MainComponent>(), SettingContract.View {

    private val progressDialog by lazy(NONE) {
        ProgressDialog(context).apply {
            setTitle("백업")
            setMessage("데이터를 백업중입니다...")
        }
    }

    private var isFirst = true

    companion object {
        private val fragment by lazy { SettingFragment() }
        fun getInstance(): SettingFragment {
            return fragment
        }
    }

    @Inject
    lateinit var settingPresenter: SettingPresenter<SettingContract.View>

    override fun setComponent(activityComponent: MainComponent) {
        activityComponent
                .plus(SettingModule())
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_setting, container, false)
        settingPresenter.attachView(this)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        settingPresenter.initSetting()

        switch_alarm.setOnCheckedChangeListener { view, isChecked ->
            settingPresenter.setOnCheckedPushChangeListener(view, isChecked)
        }

        spinner_theme.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (isFirst) {
                    isFirst = false
                    return
                }
                settingPresenter.setOnThemeSelectedListener(parent, view, position, id)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        btn_backup.setOnClickListener {
            settingPresenter.checkStoragePermission(this, REQUEST_BACKUP)
        }

        btn_load.setOnClickListener {
            settingPresenter.checkStoragePermission(this, REQUEST_RESTORE)
        }
    }

    override fun setAlarmSwitch(isChecked: Boolean) {
        switch_alarm.isChecked = isChecked
    }

    override fun setThemeView(theme: Int) {
        spinner_theme.setSelection(theme)
    }

    override fun changeTheme(theme: Int) {
        isFirst = true
        ThemeUtil.changeToTheme(activity)
    }

    override fun onDestroyView() {
        settingPresenter.detachView()
        super.onDestroyView()
    }

    override fun showBackUpDialog() {
        val dialog = RestoreDialogFragment()
        dialog.show(fragmentManager, RestoreDialogFragment::class.java.simpleName)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        settingPresenter.onRequestPermissionsResult(context, requestCode, permissions, grantResults)
    }

    override fun showRationalDialog() {
        val deniedMessage = getString(R.string.permission_storage_message)
        PermissionUtil.showRationalDialog(context, deniedMessage)
    }

    override fun showToast(message: String) {
        toast(message)
    }

    override fun showProgress() {
        if(!progressDialog.isShowing)
            progressDialog.show()
    }

    override fun dismissProgress() {
        if(progressDialog.isShowing)
            progressDialog.dismiss()
    }
}
