package com.github.ojh.overtime.base

import android.app.Application
import android.support.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import com.github.ojh.overtime.R
import com.github.ojh.overtime.pin.CustomPinActivity
import com.github.ojh.overtime.util.RealmUtil
import com.github.orangegangsters.lollipin.lib.managers.LockManager
import com.google.android.gms.ads.MobileAds
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import io.fabric.sdk.android.Fabric


class OverTimeApplication : MultiDexApplication() {

    companion object {
        lateinit var application: Application
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        application = this

        val lockManager = LockManager.getInstance()
        lockManager.enableAppLock(this, CustomPinActivity::class.java)
        lockManager.appLock.logoId = R.mipmap.ic_launcher

        Fabric.with(this, Crashlytics())
        RealmUtil.initRealm(this)
        MobileAds.initialize(this, getString(R.string.ad_app_id))


        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build()
        )

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

    }
}