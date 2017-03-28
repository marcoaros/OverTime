package com.github.ojh.overtime.main.setting


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.view.BaseFragment
import com.github.ojh.overtime.main.MainComponent
import kotlinx.android.synthetic.main.fragment_setting.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : BaseFragment<MainComponent>(), SettingContract.View {

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
            settingPresenter.setOnCheckedChangeListener(view, isChecked)
        }
    }

    override fun setAlarmSwitch(isChecked: Boolean) {
        switch_alarm.isChecked = isChecked
    }

    override fun onDestroyView() {
        settingPresenter.detachView()
        super.onDestroyView()
    }
}
