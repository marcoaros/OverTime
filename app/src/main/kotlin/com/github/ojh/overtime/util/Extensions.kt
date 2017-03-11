package com.github.ojh.overtime.util

import android.app.Activity
import android.net.Uri
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