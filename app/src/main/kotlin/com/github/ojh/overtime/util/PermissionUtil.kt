package com.github.ojh.overtime.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import com.github.ojh.overtime.R

object PermissionUtil {
    //A method that can be called from any Activity, to check for specific permission
    fun checkPermissionFromActivity(activity: Activity, permission: String, requestCode: Int) {
        //If requested permission isn't Granted yet
        if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            //Request permission from user
            ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
        } else {
            ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
        }
    }

    fun showRationalDialog(context: Context, message: String) {
        val dialogBuilder = AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.permission_dialog_rational_title))
                .setMessage(message)
                .setNegativeButton(context.getString(R.string.permission_dialog_rational_negative_text), null)
                .setPositiveButton(context.getString(R.string.permission_dialog_rational_positive_text)) { dialog, which ->
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