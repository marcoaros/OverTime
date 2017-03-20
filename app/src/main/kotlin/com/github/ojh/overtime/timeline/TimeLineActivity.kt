package com.github.ojh.overtime.timeline

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.BaseActivity
import com.github.ojh.overtime.data.model.DeleteEvent
import com.github.ojh.overtime.data.model.TimeLine.Companion.KEY_TIMELINE_ID
import com.github.ojh.overtime.data.model.UpdateEvent
import com.github.ojh.overtime.data.model.WriteEvent
import com.github.ojh.overtime.detail.DetailActivity
import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.setting.TimeLineSettingDialog
import com.github.ojh.overtime.timeline.adapter.TimeLineAdapter
import com.github.ojh.overtime.util.EventBus
import com.github.ojh.overtime.util.VerticalSpaceItemDecoration
import com.github.ojh.overtime.write.WriteActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_timeline.view.*
import javax.inject.Inject

class TimeLineActivity : BaseActivity(), TimeLineContract.View {

    @Inject
    lateinit var presenter: TimeLinePresenter<TimeLineContract.View>

    private val timeLineAdapter by lazy {
        TimeLineAdapter()
    }

    override fun setComponent(appComponent: AppComponent) {
        DaggerTimeLineComponent.builder()
                .appComponent(appComponent)
                .timeLineModule(TimeLineModule(timeLineAdapter))
                .build()
                .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)

        initRecyclerView()
        initEventBus()
        initTimeLines()
        initEventListener()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        rv_timeline.layoutManager = layoutManager
        val itemDecoration = VerticalSpaceItemDecoration(
                resources.getDimensionPixelSize(R.dimen.item_vertical_space)
        )
        rv_timeline.addItemDecoration(itemDecoration)
        rv_timeline.adapter = timeLineAdapter
    }

    private fun initEventBus() {
        EventBus.bus.subscribe {
            when (it) {
                is WriteEvent -> presenter.addTimeLine(it.timeLine, 0)
                is UpdateEvent -> presenter.updateTimeLine(it.timeLine)
                is DeleteEvent -> presenter.deleteTimeLine(it.timeLineId)
            }
        }
    }

    private fun initTimeLines() {
        presenter.getTimeLines()
    }

    private fun initEventListener() {
        presenter.initEventListener()
        fab_write.setOnClickListener { navigateToWrite() }
    }

    override fun navigateToWrite() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions.makeSceneTransitionAnimation(this, fab_write, fab_write.transitionName)
            startActivity(Intent(this, WriteActivity::class.java), options.toBundle())
        } else {
            startActivity(Intent(this, WriteActivity::class.java))
        }
    }

    override fun navigateToSetting(timeLineId: Int) {
        val dialog = TimeLineSettingDialog.newInstance(timeLineId)
        dialog.show(supportFragmentManager, TimeLineSettingDialog::class.java.simpleName)
    }

    override fun navigateToDetail(view: View, timeLineId: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(KEY_TIMELINE_ID, timeLineId)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                && view.iv_timeline_image.visibility == View.VISIBLE) {

            val options = ActivityOptions.makeSceneTransitionAnimation(
                    this, view.iv_timeline_image, view.iv_timeline_image.transitionName
            )

            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

    override fun scrollToPosition(position: Int) {
        rv_timeline.scrollToPosition(position)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}
