package com.github.ojh.overtime.write

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.data.Events
import com.github.ojh.overtime.data.TimeLine
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

    private var tempImgFile: File? = null

    override fun initTimeLine(timeLine: TimeLine) {
        this.timeLine = timeLine
        getView()?.initView(timeLine)
    }

    override fun saveTimeLine() {
        if (isValidTimeLine) {
            if (isUpdate) {
                dataManager.updateTimeLine(timeLine)
                EventBus.post(Events.UpdateEvent(timeLine))
            } else {
                dataManager.saveTimeLine(timeLine)
                EventBus.post(Events.WriteEvent(timeLine))
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
                tempImgFile = File(Environment.getExternalStorageDirectory(), "temp_" + System.currentTimeMillis() / 1000 + ".jpg")
                getView()?.navigateToGallery(Uri.fromFile(tempImgFile))

            } else {
                getView()?.showRationalDialog()
            }
        }
    }

    override fun onActivityResult(context: Context, requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {

            val internalFile = File("${context.filesDir}/images", "img_" + System.currentTimeMillis() / 1000 + ".jpg")
            tempImgFile?.copyTo(internalFile)

            tempImgFile?.let {
                if (it.exists()) {
                    it.delete()
                }
            }

            val uri = Uri.fromFile(internalFile)
            timeLine.mImgUri = uri.toString()
            getView()?.loadCroppedImage(uri)
        }
    }
}