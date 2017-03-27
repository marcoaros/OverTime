package com.github.ojh.overtime.main.timeline

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.view.animation.OvershootInterpolator
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.view.BaseFragment
import com.github.ojh.overtime.data.Events
import com.github.ojh.overtime.data.TimeLine
import com.github.ojh.overtime.detail.DetailActivity
import com.github.ojh.overtime.edit.EditDialogFragment
import com.github.ojh.overtime.main.MainComponent
import com.github.ojh.overtime.main.timeline.adapter.TimeLineAdapter
import com.github.ojh.overtime.util.EventBus
import com.github.ojh.overtime.util.VerticalSpaceItemDecoration
import com.github.ojh.overtime.util.startActivityWithTransition
import com.github.ojh.overtime.util.toFilterType
import io.reactivex.android.schedulers.AndroidSchedulers
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.android.synthetic.main.fragment_timeline.*
import kotlinx.android.synthetic.main.view_timeline.view.*
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

class TimeLineFragment private constructor() : BaseFragment<MainComponent>(), TimeLineContract.View {

    companion object {
        private val fragment by lazy(NONE) { TimeLineFragment() }

        fun getInstance() = fragment
    }

    @Inject
    lateinit var filterAdapter: ArrayAdapter<CharSequence>

    @Inject
    lateinit var presenter: TimeLinePresenter<TimeLineContract.View>

    private val timeLineAdapter by lazy(NONE) {
        TimeLineAdapter()
    }

    override fun setComponent(activityComponent: MainComponent) {
        activityComponent
                .plus(TimeLineModule(timeLineAdapter))
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

        val filterType = presenter.getFilterType()
        spinnerFilter.setSelection(filterType.toPosition())

        if (presenter.getItemCount() == 0) {
            presenter.getTimeLines(filterType)
        }

        spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            var isFirst = true

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (isFirst) {
                    isFirst = false
                    return
                }

                presenter.getTimeLines(position.toFilterType())
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

        rv_timeline.itemAnimator = SlideInLeftAnimator().apply {
            addDuration = 500
            setInterpolator(OvershootInterpolator(0.5f))
        }

        val alphaAdapter = SlideInRightAnimationAdapter(timeLineAdapter).apply {
            setFirstOnly(false)
            setDuration(500)
            setInterpolator(OvershootInterpolator(0.5f))
        }
        rv_timeline.adapter = alphaAdapter
    }

    private fun initEventBus() {
        EventBus.bus
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    when (it) {
                        is Events.WriteEvent -> presenter.addTimeLine(it.timeLine)
                        is Events.UpdateEvent -> presenter.updateTimeLine(it.timeLine)
                        is Events.DeleteEvent -> presenter.deleteTimeLine(it.timeLineId)
                    }
                }
    }

    private fun initEventListener() {
        presenter.initEventListener()
    }

    override fun navigateToSetting(timeLineId: Int) {
        val dialog = EditDialogFragment.newInstance(timeLineId)
        dialog.show(fragmentManager, EditDialogFragment::class.java.simpleName)
    }

    override fun navigateToDetail(view: View, timeLineId: Int) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(TimeLine.KEY_TIMELINE_ID, timeLineId)

        if (view.iv_timeline_image.visibility == View.VISIBLE) {
            startActivityWithTransition(intent, view.iv_timeline_image)
        } else {
            startActivity(intent)
        }
    }

    override fun scrollToPosition(position: Int) {
        rv_timeline.smoothScrollToPosition(position)
    }
}