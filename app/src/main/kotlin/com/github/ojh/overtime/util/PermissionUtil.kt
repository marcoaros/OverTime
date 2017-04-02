package com.github.ojh.overtime.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import com.github.ojh.overtime.R

object PermissionUtil {

    const val REQUEST_STORAGE = 111

    fun checkPermissionFromActivity(activity: Activity, permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
    }

    fun checkPermissionFromFragment(fragment: Fragment, permission: String, requestCode: Int) {
        fragment.requestPermissions(arrayOf(permission), requestCode)
    }


    fun showRationalDialog(context: Context, message: String) {
        val dialogBuilder = AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.permission_dialog_rational_title))
                .setMessage(message)
                .setNegativeButton(context.getString(R.string.permission_dialog_rational_negative_text), null)
                .setPositiveButton(context.getString(R.string.permission_dialog_rational_positive_text)) { _, _ ->
                    try {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                .setData(Uri.parse("package:" + context.packageName))
                        context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        e.printStackTrace()
                        val intent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
                        context.startActivity(intent)
                    }
                }.setCancelable(false)
        val dialog = dialogBuilder.create()
        dialog.show()
    }
}