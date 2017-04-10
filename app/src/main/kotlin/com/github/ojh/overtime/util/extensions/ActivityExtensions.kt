package com.github.ojh.overtime.util.extensions

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Pair
import android.view.View
import android.widget.Toast

fun Activity.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun AppCompatActivity.addFragment(@IdRes containerViewId: Int, vararg fragments: Fragment) {
    fragments.forEach {
        supportFragmentManager.beginTransaction()
                .add(containerViewId, it)
                .commit()
    }
}

fun AppCompatActivity.showFragment(vararg fragments: Fragment) {
    fragments.forEach {
        supportFragmentManager.beginTransaction()
                .show(it)
                .commit()
    }
}

fun AppCompatActivity.hideFragment(vararg fragments: Fragment) {
    fragments.forEach {
        supportFragmentManager.beginTransaction()
                .hide(it)
                .commit()
    }
}

fun AppCompatActivity.replaceFragment(@IdRes containerViewId: Int, fragment: Fragment, hasBackStack: Boolean = true) {
    if(hasBackStack) {
        supportFragmentManager.beginTransaction()
                .replace(containerViewId, fragment)
                .addToBackStack(null)
                .commit()
    } else {
        supportFragmentManager.beginTransaction()
                .replace(containerViewId, fragment)
                .commit()
    }
}

fun AppCompatActivity.startActivityWithTransition(intent: Intent, vararg views: View) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

        val pairs = views.map {
            Pair.create(it, it.transitionName)
        }.toTypedArray()

        val options = ActivityOptions.makeSceneTransitionAnimation(this, *pairs)
        startActivity(intent, options.toBundle())

    } else {
        startActivity(intent)
    }
}

fun Fragment.startActivityWithTransition(intent: Intent, vararg views: View) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

        val pairs = views.map {
            Pair.create(it, it.transitionName)
        }.toTypedArray()

        val options = ActivityOptions.makeSceneTransitionAnimation(activity, *pairs)
        startActivity(intent, options.toBundle())

    } else {
        startActivity(intent)
    }
}

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(activity, message, duration).show()
}

fun Intent.cropIntent(uri: Uri, aspectX: Int, aspectY: Int): Intent = this.apply {
    action = android.content.Intent.ACTION_PICK
    data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    type = "image/*"
    putExtra("crop", "true")
    putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri)
    putExtra("outputFormat", android.graphics.Bitmap.CompressFormat.JPEG.toString())
    putExtra("aspectX", aspectX)
    putExtra("aspectY", aspectY)
}

