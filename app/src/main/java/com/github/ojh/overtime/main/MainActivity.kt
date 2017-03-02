package com.github.ojh.overtime.main

import android.os.Bundle
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.BaseActivity
import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.toast
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainPresenter<MainContract.View>

    override fun setComponent(appComponent: AppComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .build().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)

        fb_write.setOnClickListener {
            presenter.clickFabWrite()
        }
    }

    override fun showToast(message: String) {
        toast(message)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}
