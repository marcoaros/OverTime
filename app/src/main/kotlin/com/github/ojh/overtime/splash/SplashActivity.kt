package com.github.ojh.overtime.splash

import android.content.Intent
import android.os.Bundle
import com.airbnb.lottie.LottieAnimationView
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.view.BaseActivity
import com.github.ojh.overtime.app.AppComponent
import com.github.ojh.overtime.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashContract.View {

    @Inject
    lateinit var presenter: SplashPresenter<SplashContract.View>

    val lottieViews by lazy(LazyThreadSafetyMode.NONE) {
        arrayOf<LottieAnimationView>(
                lottie_o, lottie_v, lottie_e1, lottie_r, lottie_t, lottie_i, lottie_m, lottie_e2
        )
    }

    override fun setComponent(appComponent: AppComponent): SplashComponent {
        val component = DaggerSplashComponent.builder()
                .appComponent(appComponent)
                .build()

        component.inject(this)

        return component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        presenter.attachView(this)
        presenter.init(lottieViews)
    }

    override fun navigateToTimeLine() {
        val timeLineIntent = Intent(this, MainActivity::class.java)
        startActivity(timeLineIntent)
        finish()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}