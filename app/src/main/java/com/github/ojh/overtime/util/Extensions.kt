package com.github.ojh.overtime.util

import android.app.Activity
import android.support.v4.app.Fragment
import android.widget.Toast

/**
 * Created by ohjaehwan on 2017. 2. 28..
 */
fun Activity.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(activity, message, duration).show()
}