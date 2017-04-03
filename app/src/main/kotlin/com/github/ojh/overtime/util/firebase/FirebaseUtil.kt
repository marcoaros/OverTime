package com.github.ojh.overtime.util.firebase

import com.github.ojh.overtime.BuildConfig
import com.github.ojh.overtime.R
import com.github.ojh.overtime.util.SimpleCallback
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlin.LazyThreadSafetyMode.NONE

/**
 * Created by OhJaeHwan on 2017-04-03.
 */
object FirebaseUtil {

    val configSettings: FirebaseRemoteConfigSettings by lazy(NONE) {
        FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build()
    }

    val firebaseRemoteConfig: FirebaseRemoteConfig by lazy(NONE) {
        FirebaseRemoteConfig.getInstance().apply {
            setConfigSettings(configSettings)
            setDefaults(R.xml.remote_config_defaults)
        }
    }

    val cacheExpiration by lazy(NONE) {
        if (firebaseRemoteConfig.info.configSettings.isDeveloperModeEnabled) {
            0L
        } else {
            3600L
        }
    }

    fun fetch(successCallback: SimpleCallback? = null, failCallback: SimpleCallback? = null) {
        firebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener({
                    if (it.isSuccessful) {
                        firebaseRemoteConfig.activateFetched()
                        successCallback?.invoke()
                    } else {
                        failCallback?.invoke()
                    }
                })

    }

    fun getString(key: String): String {
        return firebaseRemoteConfig.getString(key)
    }
}