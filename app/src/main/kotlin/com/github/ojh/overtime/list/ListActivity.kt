package com.github.ojh.overtime.list

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.OvershootInterpolator
import com.github.ojh.overtime.R
import com.github.ojh.overtime.app.AppComponent
import com.github.ojh.overtime.base.ActivityComponent
import com.github.ojh.overtime.base.view.BaseActivity
import com.github.ojh.overtime.data.TimeLine
import com.github.ojh.overtime.detail.DetailActivity
import com.github.ojh.overtime.edit.EditDialogFragment
import com.github.ojh.overtime.main.ListContract
import com.github.ojh.overtime.main.ListModule
import com.github.ojh.overtime.main.calendar.CalendarFragment.Companion.KEY_DATE
import com.github.ojh.overtime.main.timeline.adapter.TimeLineAdapter
import com.github.ojh.overtime.util.VerticalSpaceItemDecoration
import com.github.ojh.overtime.util.extensions.startActivityWithTransition
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.view_timeline.view.*
import java.util.*
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

class ListActivity : BaseActivity(), ListContract.View {

    @Inject
    lateinit var presenter: ListContract.Presenter<ListContract.View>

    private val timeLineAdapter by lazy(NONE) {
        TimeLineAdapter()
    }

    override fun setComponent(appComponent: AppComponent): ActivityComponent {
        val component = appComponent.plus(ListModule(timeLineAdapter))
        component.inject(this)
        return component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        presenter.attachView(this)

        val date = intent.getSerializableExtra(KEY_DATE) as Date

        initToolbar()
        initRecyclerView()
        initEventListener()
        presenter.initDate(date)
        presenter.getTimeLines()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        rv_list.layoutManager = layoutManager
        val itemDecoration = VerticalSpaceItemDecoration(
                resources.getDimensionPixelSize(R.dimen.item_vertical_space)
        )
        rv_list.addItemDecoration(itemDecoration)

        rv_list.itemAnimator = SlideInLeftAnimator().apply {
            addDuration = 500
            setInterpolator(OvershootInterpolator(0.5f))
        }

        val alphaAdapter = SlideInRightAnimationAdapter(timeLineAdapter).apply {
            setFirstOnly(false)
            setDuration(500)
            setInterpolator(OvershootInterpolator(0.5f))
        }
        rv_list.adapter = alphaAdapter
    }

    private fun initEventListener() {
        presenter.initEventListener()
    }

    override fun navigateToDetail(view: View, timeLineId: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(TimeLine.KEY_TIMELINE_ID, timeLineId)

        if (view.iv_timeline_image.visibility == View.VISIBLE) {
            startActivityWithTransition(intent, view.iv_timeline_image)
        } else {
            startActivity(intent)
        }
    }

    override fun navigateToSetting(timeLineId: Int) {
        val dialog = EditDialogFragment.newInstance(timeLineId)
        dialog.show(supportFragmentManager, EditDialogFragment::class.java.simpleName)
    }

    override fun scrollToPosition(position: Int) {
        rv_list.smoothScrollToPosition(position)
    }
}
