package com.github.ojh.overtime.util

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.ojh.overtime.ads.AdDialog

class BackPressCloseHandler(private val activity: AppCompatActivity) {

    private var backKeyPressedTime: Long = 0
    private var toast: Toast? = null

    fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            showGuide()
            return
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            val dialog = AdDialog()
            dialog.show(activity.supportFragmentManager, AdDialog::class.java.simpleName)

            toast?.cancel()
        }
    }

    fun showGuide() {
        toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)
        toast?.show()
    }
}