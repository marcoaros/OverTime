package com.github.ojh.overtime.detail

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionInflater
import android.view.MenuItem
import android.view.View
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.view.BaseActivity
import com.github.ojh.overtime.data.TimeLine
import com.github.ojh.overtime.data.TimeLine.Companion.KEY_TIMELINE_ID
import com.github.ojh.overtime.app.AppComponent
import com.github.ojh.overtime.util.extensions.load
import com.github.ojh.overtime.util.extensions.toFormatString
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : BaseActivity<DetailComponent>(), DetailContract.View {

    @Inject
    lateinit var presenter: DetailPresenter<DetailContract.View>

    override fun setComponent(appComponent: AppComponent): DetailComponent {
        val component = DaggerDetailComponent.builder()
                .appComponent(appComponent)
                .build()

        component.inject(this)

        return component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        presenter.attachView(this)

        val timeLineId = intent.getIntExtra(KEY_TIMELINE_ID, -1)
        if (timeLineId == -1) {
            finish()
        }

        presenter.init(timeLineId)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initAnimation()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun initAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val transition = TransitionInflater.from(this)
                    .inflateTransition(R.transition.change_images_transform)

            window.sharedElementEnterTransition = transition

            window.enterTransition = Fade().apply {
                duration = resources.getInteger(R.integer.animation_duration).toLong()
            }

            window.returnTransition = Fade().apply {
                duration = resources.getInteger(R.integer.animation_duration).toLong()
            }
        }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun initView(timeLine: TimeLine) {
        with(timeLine) {
            if (mImgUri != null) {
                iv_img.load(Uri.parse(mImgUri))
            } else {
                iv_img.visibility = View.GONE
            }

            mContent?.let { tv_content.text = it }
            mDate?.let { tv_date.text = it.toFormatString() }
        }
    }
}
