package com.github.ojh.overtime.util.theme

import android.app.Activity
import android.content.Intent
import com.github.ojh.overtime.R
import com.github.ojh.overtime.splash.SplashActivity


object ThemeUtil {

    fun changeToTheme(activity: Activity) {
        activity.finish()
        activity.startActivity(Intent(activity, SplashActivity::class.java))
        activity.overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out)
    }

    fun onActivityCreateSetTheme(activity: Activity, theme: Int) {
        when (theme) {
            1 -> activity.setTheme(R.style.PurpleTheme_NoActionBar)
            2 -> activity.setTheme(R.style.YellowTheme_NoActionBar)
            else -> activity.setTheme(R.style.BrownTheme_NoActionBar)
        }
    }
}