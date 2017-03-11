package com.github.ojh.overtime.write

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.data.model.Events
import com.github.ojh.overtime.data.model.TimeLine
import com.github.ojh.overtime.util.EventBus
import com.github.ojh.overtime.util.PermissionUtil
import com.github.ojh.overtime.write.WriteContract.Companion.REQUEST_GALLERY
import io.reactivex.disposables.CompositeDisposable
import java.io.File
import java.util.*
import javax.inject.Inject

class WritePresenter<V : WriteContract.View> @Inject constructor(
        dataManager: DataManager,
        compositeDisposable: CompositeDisposable

) : BasePresenter<V>(dataManager, compositeDisposable), WriteContract.Presenter<V> {

    private var mSavedFile: File? = null
    private var mContent: String? = null

    override fun init() {
        getView()?.initView()
    }

    override fun clickSave() {
        val timeLine = TimeLine().apply {
            content = mContent
            date = Date()
            imgUri = Uri.fromFile(mSavedFile).toString()
        }
        if (timeLine.isValidAll()) {
            dataManager.saveTimeLine(timeLine)
            EventBus.post(Events.WriteEvent(timeLine))
            getView()?.navigateToMain()
        } else {
            getView()?.setErrorContent(true)
        }
    }

    override fun onContentTextChanged(changedContent: String) {
        val isError = changedContent.isEmpty()
        getView()?.setErrorContent(isError)
        mContent = changedContent
    }

    override fun checkStoragePermission(activity: Activity) {
        PermissionUtil.checkPermissionFromActivity(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_GALLERY)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_GALLERY) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mSavedFile = File(Environment.getExternalStorageDirectory(), "temp_" + System.currentTimeMillis() / 1000 + ".jpg")
                getView()?.navigateToGallery(Uri.fromFile(mSavedFile))

            } else {
                getView()?.showRationalDialog()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            mSavedFile?.let {
                if(it.exists()) {
                    it.delete()
                }
            }
            getView()?.loadCroppedImage(Uri.fromFile(mSavedFile))
        }
    }
}