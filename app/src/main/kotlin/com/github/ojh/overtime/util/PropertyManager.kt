package com.github.ojh.overtime.util

import android.content.Context
import android.preference.PreferenceManager
import com.f2prateek.rx.preferences2.RxSharedPreferences
import io.reactivex.Observable
import kotlin.LazyThreadSafetyMode.NONE

class PropertyManager(context: Context) {

    companion object{
        val KEY_ALARM = "key_alarm"
        val KEY_THEME = "key_theme"
    }

    private val pref by lazy(NONE) {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    private val rxPref by lazy {
        RxSharedPreferences.create(pref)
    }

    private val editor by lazy(NONE) {
        pref.edit()
    }

    fun setBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Observable<Boolean> {
        return rxPref.getBoolean(key, defaultValue).asObservable()
    }

    fun setInt(key: String, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(key: String, defaultValue: Int = 0): Observable<Int> {
        return rxPref.getInteger(key, defaultValue).asObservable()
    }

    fun getIntSync(key: String, defaultValue: Int = 0): Int {
        return pref.getInt(key, defaultValue)
    }
}
