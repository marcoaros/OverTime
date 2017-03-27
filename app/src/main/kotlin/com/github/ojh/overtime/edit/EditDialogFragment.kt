package com.github.ojh.overtime.edit

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.view.BaseDialogFragment
import com.github.ojh.overtime.data.TimeLine
import com.github.ojh.overtime.data.TimeLine.Companion.KEY_TIMELINE_ID
import com.github.ojh.overtime.main.MainComponent
import com.github.ojh.overtime.write.WriteActivity
import kotlinx.android.synthetic.main.fragment_dialog_timeline.*
import org.parceler.Parcels
import javax.inject.Inject

class EditDialogFragment private constructor() : BaseDialogFragment<MainComponent>(), EditContract.View {

    companion object {
        fun newInstance(timeLineId: Int): EditDialogFragment {
            val dialog = EditDialogFragment()
            val args = Bundle()
            args.putInt(KEY_TIMELINE_ID, timeLineId)
            dialog.arguments = args
            return dialog
        }
    }

    @Inject
    lateinit var presenter: EditPresenter<EditContract.View>

    override fun setComponent(activityComponent: MainComponent) {
        activityComponent
                .plus(EditModule())
                .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_dialog_timeline, container, false)
        presenter.attachView(this)
        return view
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initArguments()
        initEventListener()
    }

    private fun initArguments() {
        val timeLineId = arguments.getInt(KEY_TIMELINE_ID)
        presenter.init(timeLineId)
    }

    private fun initEventListener() {
        btn_update.setOnClickListener {
            presenter.updateTimeLine()
        }

        btn_delete.setOnClickListener {
            presenter.deleteTimeLine()
        }

        btn_cancel.setOnClickListener {
            presenter.dismiss()
        }
    }

    override fun dismissDialog() {
        dismiss()
    }

    override fun navigateToWrite(timeLine: TimeLine) {
        val intent = Intent(context, WriteActivity::class.java)
        intent.putExtra(TimeLine.KEY_TIMELINE, Parcels.wrap(TimeLine::class.java, timeLine))
        startActivity(intent)
    }
}