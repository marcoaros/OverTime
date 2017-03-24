package com.github.ojh.overtime.main.timeline

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Pair
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.BaseFragment
import com.github.ojh.overtime.data.Events
import com.github.ojh.overtime.data.TimeLine
import com.github.ojh.overtime.detail.DetailActivity
import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.main.timeline.adapter.TimeLineAdapter
import com.github.ojh.overtime.setting.TimeLineSettingDialog
import com.github.ojh.overtime.util.EventBus
import com.github.ojh.overtime.util.VerticalSpaceItemDecoration
import com.github.ojh.overtime.util.toFilterType
import com.github.ojh.overtime.write.WriteActivity
import kotlinx.android.synthetic.main.fragment_timeline.*
import kotlinx.android.synthetic.main.view_timeline.view.*
import javax.inject.Inject

class TimeLineFragment private constructor() : BaseFragment(), TimeLineContract.View {


    companion object {
        private val fragment by lazy { TimeLineFragment() }

        fun getInstance(): TimeLineFragment {
            return fragment
        }
    }


    @Inject
    lateinit var filterAdapter: ArrayAdapter<CharSequence>

    @Inject
    lateinit var presenter: TimeLinePresenter<TimeLineContract.View>

    private val timeLineAdapter by lazy(LazyThreadSafetyMode.NONE) {
        TimeLineAdapter()
    }

    override fun setComponent(appComponent: AppComponent) {
        DaggerTimeLineComponent.builder()
                .appComponent(appComponent)
                .timeLineModule(TimeLineModule(timeLineAdapter))
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_timeline, container, false)
        presenter.attachView(this)
        return view
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_timeline, menu)
        val item = menu?.findItem(R.id.menu_filter)

        val spinnerFilter = MenuItemCompat.getActionView(item) as Spinner
        spinnerFilter.adapter = filterAdapter
        spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                TimeLineFragment.getInstance().presenter.getTimeLines(position.toFilterType())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        initRecyclerView()
        initEventBus()
        initEventListener()
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
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
    }

    override fun navigateToWrite() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val p1 = with(fab_write) {
                Pair.create<View, String>(this, this.transitionName)
            }

            val options = ActivityOptions.makeSceneTransitionAnimation(activity, p1)
            startActivity(Intent(context, WriteActivity::class.java), options.toBundle())
        } else {
            startActivity(Intent(context, WriteActivity::class.java))
        }
    }

    override fun navigateToSetting(timeLineId: Int) {
        val dialog = TimeLineSettingDialog.newInstance(timeLineId)
        dialog.show(fragmentManager, TimeLineSettingDialog::class.java.simpleName)
    }

    override fun navigateToDetail(view: View, timeLineId: Int) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(TimeLine.KEY_TIMELINE_ID, timeLineId)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                && view.iv_timeline_image.visibility == View.VISIBLE) {

            val p1 = with(view.iv_timeline_image) {
                Pair.create<View, String>(this, this.transitionName)
            }

            val options = ActivityOptions.makeSceneTransitionAnimation(activity, p1)
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