package com.github.ojh.overtime.write

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.github.ojh.overtime.base.BaseContract
import com.github.ojh.overtime.data.TimeLine

interface WriteContract {
    companion object {
        const val REQUEST_GALLERY = 200
    }
    interface View : BaseContract.View {
        fun initView(timeLine: TimeLine)
        fun setErrorContent(isError: Boolean)
        fun navigateToMain()
        fun navigateToGallery(uri: Uri)
        fun loadCroppedImage(uri: String)
        fun showRationalDialog()
    }

    interface Presenter<V : View> : BaseContract.Presenter<V> {
        fun initTimeLine(timeLine: TimeLine)
        fun saveTimeLine()
        fun onContentTextChanged(changedContent: String)
        fun checkStoragePermission(activity: Activity)
        fun onRequestPermissionsResult(context: Context, requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
        fun onActivityResult(context: Context, requestCode: Int, resultCode: Int, data: Intent?)
    }
}