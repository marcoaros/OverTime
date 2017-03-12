package com.github.ojh.overtime.write

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.WindowManager
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.BaseActivity
import com.github.ojh.overtime.data.model.TimeLine
import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.util.PermissionUtil
import com.github.ojh.overtime.util.SimpleTextWatcher
import com.github.ojh.overtime.util.cropIntent
import com.github.ojh.overtime.util.load
import com.github.ojh.overtime.write.WriteContract.Companion.REQUEST_GALLERY
import kotlinx.android.synthetic.main.activity_write.*
import javax.inject.Inject


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

        initTimeLine()
        initEventListener()
    }

    private fun initTimeLine() {
        writePresenter.init()
    }

    override fun initView(timeLine: TimeLine) {
        timeLine.content?.let {
            edit_content.setText(it)
        }

        timeLine.imgUri?.let {
            iv_gallery.load(Uri.parse(it))
        }
    }

    private fun initEventListener() {
        iv_gallery.setOnClickListener {
            writePresenter.checkStoragePermission(this)
        }

        btn_write.setOnClickListener {
            writePresenter.clickSave()
        }

        edit_content.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                writePresenter.onContentTextChanged(s.toString())
            }
        })
    }

    override fun onDestroy() {
        writePresenter.detachView()
        super.onDestroy()
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
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        writePresenter.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun showRationalDialog() {
        val deniedMessage = getString(R.string.permission_storage_message)
        PermissionUtil.showRationalDialog(this, deniedMessage)
    }

    override fun navigateToGallery(uri: Uri) {
        val intent = Intent().cropIntent(uri, iv_gallery.width, iv_gallery.height)
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        writePresenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun loadCroppedImage(uri: Uri) {
        iv_gallery.load(uri)
    }
}