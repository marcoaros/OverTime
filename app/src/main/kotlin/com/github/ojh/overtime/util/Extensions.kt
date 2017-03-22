package com.github.ojh.overtime.util

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.transition.Transition
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

fun Activity.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(activity, message, duration).show()
}

fun ImageView.load(uri: Uri) {
    Glide.with(context)
            .load(uri)
            .centerCrop()
            .into(this)
}

fun Date.toFormatString(format: String = "yyyy-MM-dd"): String {
    val formatter = SimpleDateFormat(format, Locale.KOREA)
    return formatter.format(this)
}

fun EditText.setOnSimpleTextWather(listener: (text: String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            listener.invoke(s.toString())
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
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

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun Transition.addSimpleEndTransitionListener(action: ()-> Unit) {
    this.addListener(object : Transition.TransitionListener {
        override fun onTransitionEnd(transition: Transition) {
            removeListener(this)
            action.invoke()
        }
        override fun onTransitionCancel(transition: Transition) {
            removeListener(this)
            action.invoke()
        }
        override fun onTransitionPause(transition: Transition) {
            removeListener(this)
            action.invoke()
        }
        override fun onTransitionResume(transition: Transition) {}
        override fun onTransitionStart(transition: Transition) {}
    })
}