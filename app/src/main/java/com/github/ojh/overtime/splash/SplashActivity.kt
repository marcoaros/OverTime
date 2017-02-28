package com.github.ojh.overtime.splash

import android.os.Bundle
import com.github.ojh.overtime.OverTimeApplication
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.BaseActivity
import com.github.ojh.overtime.splash.di.DaggerSplashComponent
import com.github.ojh.overtime.toast
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity<SplashContract.View, SplashPresenter, SplashComponent>(), SplashContract.View {

    override fun createComponent(): SplashComponent = DaggerSplashComponent.builder()
                    .appComponent(OverTimeApplication.appComponent)
                    .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        btn_next.setOnClickListener {
            presenter.clickButton()
        }
    }

    override fun showToast(message: String) {
        toast(message)
    }

}