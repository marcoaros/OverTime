package com.github.ojh.overtime.timeline

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.BaseActivity
import com.github.ojh.overtime.data.model.Events
import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.timeline.adapter.TimeLineAdapter
import com.github.ojh.overtime.timeline.adapter.VerticalSpaceItemDecoration
import com.github.ojh.overtime.util.EventBus
import com.github.ojh.overtime.write.WriteActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class TimeLineActivity : BaseActivity(), TimeLineContract.View {

    @Inject
    lateinit var presenter: TimeLinePresenter<TimeLineContract.View>

    private val timeLineAdapter by lazy { TimeLineAdapter() }

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
        rv_main.layoutManager = layoutManager
        val itemDecoration = VerticalSpaceItemDecoration(
                resources.getDimensionPixelSize(R.dimen.item_vertical_space)
        )
        rv_main.addItemDecoration(itemDecoration)
        rv_main.adapter = timeLineAdapter
    }

    private fun initEventBus() {
        EventBus.bus.subscribe {
            when (it) {
                is Events.WriteEvent -> {
                    presenter.addTimeLine(it.timeLine, 0)
                    rv_main.scrollToPosition(0)
                }
                is Events.UpdateEvent -> presenter.updateTimeLine(it.timeLine)
            }
        }
    }

    private fun initTimeLines() {
        presenter.getTimeLines()
    }

    private fun initEventListener() {
        fb_write.setOnClickListener { presenter.clickFabWrite() }
    }

    override fun showWriteDialog() {
        startActivity(Intent(this, WriteActivity::class.java))
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}
