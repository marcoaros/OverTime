package com.github.ojh.overtime.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.BaseDialogFragment
import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.toast
import javax.inject.Inject

/**
 * Created by ohjaehwan on 2017. 3. 6..
 */
class WriteDialog : BaseDialogFragment(), WriteContract.View {

    @Inject
    lateinit var writePresenter: WritePresenter<WriteContract.View>

    override fun setComponent(appComponent: AppComponent) {
        DaggerWriteComponent.builder()
                .appComponent(appComponent)
                .writeModule(WriteModule())
                .build()
                .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.CustomDialog)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_write, container, false)
        writePresenter.attachView(this)
        writePresenter.init()
        return view
    }

    override fun onDestroyView() {
        writePresenter.detachView()
        super.onDestroyView()
    }

    override fun initView() {
        toast("다이얼로그!")
    }
}