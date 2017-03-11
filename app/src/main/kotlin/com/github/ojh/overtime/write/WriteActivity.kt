package com.github.ojh.overtime.write

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.BaseActivity
import com.github.ojh.overtime.data.model.TimeLine
import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.util.PermissionUtil
import com.github.ojh.overtime.util.SimpleTextWatcher
import com.github.ojh.overtime.util.load
import com.github.ojh.overtime.util.toast
import kotlinx.android.synthetic.main.activity_write.*
import java.io.File
import java.util.*
import javax.inject.Inject


class WriteActivity : BaseActivity(), WriteContract.View {

    private val REQUEST_GALLERY = 200

    @Inject
    lateinit var writePresenter: WritePresenter<WriteContract.View>

    private var mSavedFile: File? = null

    val textWatcher: TextWatcher by lazy {
        object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                writePresenter.validateContent(s.toString())
            }
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

        iv_gallery.setOnClickListener {
            navigateToGallery(it.width, it.height)
        }

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


    private fun navigateToGallery(width: Int, height: Int) {
        PermissionUtil.checkPermissionFromActivity(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_GALLERY)
    }

    private fun cropImage() {
        mSavedFile = File(Environment.getExternalStorageDirectory(), "temp_" + System.currentTimeMillis() / 1000 + ".jpg")

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        intent.putExtra("crop", "true")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mSavedFile))
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("aspectX", iv_gallery.width)
        intent.putExtra("aspectY", iv_gallery.height)
        startActivityForResult(intent, REQUEST_GALLERY)
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

    override fun onDestroy() {
        writePresenter.detachView()
        super.onDestroy()
    }

    override fun initView() {
        toast("글쓰기!")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            iv_gallery.load(Uri.fromFile(mSavedFile))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_GALLERY) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cropImage()
            } else {
                val deniedMessage = getString(R.string.permission_storage_message)
                PermissionUtil.showRationalDialog(this, deniedMessage)
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}