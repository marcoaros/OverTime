package com.github.ojh.overtime.write

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.data.model.TimeLine
import com.github.ojh.overtime.data.model.UpdateEvent
import com.github.ojh.overtime.data.model.WriteEvent
import com.github.ojh.overtime.util.EventBus
import com.github.ojh.overtime.util.PermissionUtil
import com.github.ojh.overtime.write.WriteContract.Companion.REQUEST_GALLERY
import io.reactivex.disposables.CompositeDisposable
import java.io.File
import javax.inject.Inject

class WritePresenter<V : WriteContract.View> @Inject constructor(
        dataManager: DataManager,
        compositeDisposable: CompositeDisposable

) : BasePresenter<V>(dataManager, compositeDisposable), WriteContract.Presenter<V> {

    private lateinit var timeLine: TimeLine

    private val isValidTimeLine
        get() = timeLine.isValidAll()

    private val isUpdate
        get() = timeLine.mId != null

    private var imgFile: File? = null

    override fun init(timeLine: TimeLine) {
        this.timeLine = timeLine
        getView()?.initView(timeLine)
    }

    override fun clickSave() {
        if (isValidTimeLine) {
            if (isUpdate) {
                dataManager.updateTimeLine(timeLine)
                EventBus.post(UpdateEvent(timeLine))
            } else {
                dataManager.saveTimeLine(timeLine)
                EventBus.post(WriteEvent(timeLine))
            }
            getView()?.navigateToMain()
        } else {
            getView()?.setErrorContent(true)
        }
    }

    override fun onContentTextChanged(changedContent: String) {
        val isError = changedContent.isEmpty()
        getView()?.setErrorContent(isError)
        timeLine.mContent = changedContent
    }

    override fun checkStoragePermission(activity: Activity) {
        PermissionUtil.checkPermissionFromActivity(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_GALLERY)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_GALLERY) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                imgFile = File(Environment.getExternalStorageDirectory(), "temp_" + System.currentTimeMillis() / 1000 + ".jpg")
                getView()?.navigateToGallery(Uri.fromFile(imgFile))

            } else {
                getView()?.showRationalDialog()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            val uri = Uri.fromFile(imgFile)
            timeLine.mImgUri = uri.toString()
            getView()?.loadCroppedImage(uri)
        }
    }
}