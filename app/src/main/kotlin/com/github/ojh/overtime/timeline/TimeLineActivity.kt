package com.github.ojh.overtime.timeline

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Pair
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.BaseActivity
import com.github.ojh.overtime.data.Events
import com.github.ojh.overtime.data.TimeLine.Companion.KEY_TIMELINE_ID
import com.github.ojh.overtime.detail.DetailActivity
import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.setting.TimeLineSettingDialog
import com.github.ojh.overtime.timeline.adapter.TimeLineAdapter
import com.github.ojh.overtime.util.EventBus
import com.github.ojh.overtime.util.VerticalSpaceItemDecoration
import com.github.ojh.overtime.write.WriteActivity
import kotlinx.android.synthetic.main.activity_timeline.*
import kotlinx.android.synthetic.main.view_timeline.view.*
import javax.inject.Inject

class TimeLineActivity : BaseActivity(), TimeLineContract.View {

    @Inject
    lateinit var presenter: TimeLinePresenter<TimeLineContract.View>

    @Inject
    lateinit var filterAdapter: ArrayAdapter<CharSequence>

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
        setContentView(R.layout.activity_timeline)
        presenter.attachView(this)

        initRecyclerView()
        initFilterSpinner()
        initEventBus()
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

    private fun initFilterSpinner() {
        sp_filter.adapter = filterAdapter
    }

    private fun initEventBus() {
        EventBus.bus.subscribe {
            when (it) {
                is Events.WriteEvent -> presenter.addTimeLine(it.timeLine)
                is Events.UpdateEvent -> presenter.updateTimeLine(it.timeLine)
                is Events.DeleteEvent -> presenter.deleteTimeLine(it.timeLineId)
            }
        }
    }

    private fun initEventListener() {
        presenter.initEventListener()

        fab_write.setOnClickListener {
            presenter.clickWrite()
        }

        sp_filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.getTimeLines(position)
            }
        }
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

            val p1 = with(view.iv_timeline_image) {
                Pair.create<View, String>(this, this.transitionName)
            }

            val options = ActivityOptions.makeSceneTransitionAnimation(this, p1)
            startActivity(intent, options.toBundle())

        } else {
            startActivity(intent)
        }
    }

    override fun scrollToPosition(position: Int) {
        rv_timeline.smoothScrollToPosition(position)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}
