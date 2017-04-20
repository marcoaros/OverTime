package com.github.ojh.overtime.util

import android.content.Context
import android.preference.PreferenceManager
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import kotlin.LazyThreadSafetyMode.NONE

class PropertyManager(context: Context) {

    companion object{
        val KEY_ALARM = "key_alarm"
        val KEY_THEME = "key_theme"
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

    fun getBoolean(key: String, defaultValue: Boolean = false): Flowable<Boolean> {

        return Flowable.create( {
            pref.getBoolean(key, defaultValue)

        }, BackpressureStrategy.LATEST)
    }

    fun setInt(key: String, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(key: String): Flowable<Int> {

        return Flowable.create({
            pref.getInt(key, 0)

        }, BackpressureStrategy.LATEST)
    }

}
