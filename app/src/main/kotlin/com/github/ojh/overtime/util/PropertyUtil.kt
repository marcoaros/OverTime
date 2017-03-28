package com.github.ojh.overtime.util

import android.content.Context
import android.preference.PreferenceManager
import kotlin.LazyThreadSafetyMode.NONE

class PropertyUtil(context: Context) {

    companion object{
        val KEY_ALARM = "key_alarm"
    }

    private val pref by lazy(NONE) {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    private val editor by lazy(NONE) {
        pref.edit()
    }

    fun setBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return pref.getBoolean(key, defaultValue)
    }

}
