package com.github.ojh.overtime.timeline

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.BaseActivity
import com.github.ojh.overtime.data.model.Events
import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.timeline.adapter.TimeLineAdapter
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

        fb_write.setOnClickListener {
            presenter.clickFabWrite()
        }

        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = timeLineAdapter

        presenter.getData()

        EventBus.bus.subscribe { event ->
            when(event) {
                is Events.WriteEvent -> {
                    presenter.getData()
                }
            }
        }
    }

    override fun showWriteDialog() {
        startActivity(Intent(this, WriteActivity::class.java))
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}
