package com.github.ojh.overtime.detail

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.transition.Explode
import android.transition.TransitionInflater
import android.view.View
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.BaseActivity
import com.github.ojh.overtime.data.TimeLine
import com.github.ojh.overtime.data.TimeLine.Companion.KEY_TIMELINE_ID
import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.util.load
import com.github.ojh.overtime.util.toFormatString
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : BaseActivity(), DetailContract.View {

    @Inject
    lateinit var presenter: DetailPresenter<DetailContract.View>

    override fun setComponent(appComponent: AppComponent) {
        DaggerDetailComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this)
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
        initAnimation()
    }


    private fun initAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val transition = TransitionInflater.from(this)
                    .inflateTransition(R.transition.change_images_transform)

            window.sharedElementEnterTransition = transition

            window.enterTransition = Explode().apply {
                duration = resources.getInteger(R.integer.animation_duration).toLong()
            }

            window.returnTransition = Explode().apply {
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
