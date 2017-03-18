package com.github.ojh.overtime.detail

import android.net.Uri
import android.os.Bundle
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.BaseActivity
import com.github.ojh.overtime.data.model.TimeLine
import com.github.ojh.overtime.data.model.TimeLine.Companion.KEY_TIMELINE_ID
import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.util.load
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
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun initView(timeLine: TimeLine) {
        with(timeLine) {
            mImgUri?.let { iv_img.load(Uri.parse(it)) }
            mContent?.let { tv_content.text = it }
            mDate?.let { tv_date.text = it.toString() }
        }
    }
}
