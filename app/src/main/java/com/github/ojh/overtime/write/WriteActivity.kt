package com.github.ojh.overtime.write

import android.os.Bundle
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.BaseActivity
import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.toast
import javax.inject.Inject

/**
 * Created by ohjaehwan on 2017. 3. 6..
 */
class WriteActivity : BaseActivity(), WriteContract.View {

    @Inject
    lateinit var writePresenter: WritePresenter<WriteContract.View>

    override fun setComponent(appComponent: AppComponent) {
        DaggerWriteComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        writePresenter.attachView(this)
        writePresenter.init()
    }

    override fun onDestroy() {
        writePresenter.detachView()
        super.onDestroy()
    }

    override fun initView() {
        toast("글쓰기!")
    }
}