package com.github.ojh.overtime.util

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

fun Activity.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(activity, message, duration).show()
}

fun ImageView.load(uri: Uri) {
    Glide.with(context)
            .load(uri)
            .into(this)
}

fun Intent.cropIntent(uri: Uri, aspectX: Int, aspectY: Int): Intent = this.apply {
    action = Intent.ACTION_PICK
    data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    type = "image/*"
    putExtra("crop", "true")
    putExtra(MediaStore.EXTRA_OUTPUT, uri)
    putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
    putExtra("aspectX", aspectX)
    putExtra("aspectY", aspectY)
}