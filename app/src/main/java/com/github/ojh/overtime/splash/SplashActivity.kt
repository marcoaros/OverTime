package com.github.ojh.overtime.splash

import android.content.Intent
import android.os.Bundle
import com.github.ojh.overtime.OverTimeApplication
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.BaseActivity
import com.github.ojh.overtime.main.MainActivity

class SplashActivity : BaseActivity<SplashContract.View, SplashPresenter, SplashComponent>(), SplashContract.View {

    override fun createComponent(): SplashComponent = DaggerSplashComponent.builder()
                    .appComponent(OverTimeApplication.appComponent)
                    .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter.init()

    }

    override fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}