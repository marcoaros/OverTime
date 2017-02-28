package com.github.ojh.overtime.main

import android.os.Bundle
import com.github.ojh.overtime.OverTimeApplication
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.BaseActivity
import com.github.ojh.overtime.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainContract.View, MainPresenter, MainComponent>(), MainContract.View {

    override fun createComponent(): MainComponent = DaggerMainComponent.builder()
            .appComponent(OverTimeApplication.appComponent).build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fb_write.setOnClickListener {
            presenter.clickFabWrite()
        }

    }

    override fun showToast(message: String) {
        toast(message)
    }
}
