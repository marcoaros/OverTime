package com.github.ojh.overtime.timeline.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ojh.overtime.OverTimeApplication
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.BaseDialogFragment
import com.github.ojh.overtime.data.model.TimeLine.Companion.KEY_TIMELINE_ID
import com.github.ojh.overtime.di.AppComponent
import kotlinx.android.synthetic.main.fragment_dialog_timeline.*
import javax.inject.Inject

class TimeLineSettingDialog private constructor() : BaseDialogFragment(), TimeLineSettingDialogContract.View {

    companion object {
        fun newInstance(timeLineId: Int): TimeLineSettingDialog {
            val dialog = TimeLineSettingDialog()
            val args = Bundle()
            args.putInt(KEY_TIMELINE_ID, timeLineId)
            dialog.arguments = args
            return dialog
        }
    }

    @Inject
    lateinit var timeLineSettingDialogPresenter: TimeLineSettingDialogPresenter<TimeLineSettingDialogContract.View>

    override fun setComponent(appComponent: AppComponent) {
        DaggerTimeLineSettingDialogComponent.builder()
                .appComponent(OverTimeApplication.appComponent)
                .build()
                .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_dialog_timeline, container, false)
        timeLineSettingDialogPresenter.attachView(this)
        return view
    }

    override fun onDestroyView() {
        timeLineSettingDialogPresenter.detachView()
        super.onDestroyView()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEventListener()
    }

    private fun initEventListener() {
        btn_update.setOnClickListener {
            timeLineSettingDialogPresenter.clickUpdate()
        }

        btn_delete.setOnClickListener {
            timeLineSettingDialogPresenter.clickDelete()
        }

        btn_cancel.setOnClickListener {
            timeLineSettingDialogPresenter.clickCancel()
        }
    }

    override fun dismissDialog() {
        dismiss()
    }
}