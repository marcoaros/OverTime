package com.github.ojh.overtime.splash

import android.content.Intent
import android.os.Bundle
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.BaseActivity
import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.timeline.TimeLineActivity
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashContract.View {

    @Inject
    lateinit var presenter: SplashPresenter<SplashContract.View>

    override fun setComponent(appComponent: AppComponent) {
        DaggerSplashComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        presenter.attachView(this)
        presenter.init()
    }

    override fun navigateToTimeLine() {
        val timeLineIntent = Intent(this, TimeLineActivity::class.java)
        startActivity(timeLineIntent)
        finish()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}