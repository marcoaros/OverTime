package com.github.ojh.overtime.main.setting


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.view.BaseFragment
import com.github.ojh.overtime.main.MainComponent
import com.github.ojh.overtime.util.theme.ThemeUtil
import kotlinx.android.synthetic.main.fragment_setting.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : BaseFragment<MainComponent>(), SettingContract.View {

    var isFirst = true

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
}
