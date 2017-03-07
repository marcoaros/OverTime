package com.github.ojh.overtime.write

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.BaseActivity
import com.github.ojh.overtime.data.model.TimeLine
import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.util.toast
import kotlinx.android.synthetic.main.activity_write.*
import java.util.*
import javax.inject.Inject

/**
 * Created by ohjaehwan on 2017. 3. 6..
 */
class WriteActivity : BaseActivity(), WriteContract.View {

    @Inject
    lateinit var writePresenter: WritePresenter<WriteContract.View>

    val textWatcher: TextWatcher by lazy {
        object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                writePresenter.validateContent(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
    }

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

        btn_write.setOnClickListener {
            val timeLine = TimeLine().apply {
                date = Date()
                content = edit_content.text.toString()
                pay = 1000
            }
            writePresenter.clickSave(timeLine)
        }

        edit_content.addTextChangedListener(textWatcher)
    }

    override fun setErrorContent(isError: Boolean) {
        if (isError) {
            input_content.error = getString(R.string.write_content_error)
            requestFocus(input_content)
        } else {
            input_content.isErrorEnabled = false
        }
    }

    private fun requestFocus(view: View) {
        if (view.requestFocus()) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

    override fun navigateToMain() {
        toast("END")
        finish()
    }

    override fun onDestroy() {
        writePresenter.detachView()
        super.onDestroy()
    }

    override fun initView() {
        toast("글쓰기!")
    }
}