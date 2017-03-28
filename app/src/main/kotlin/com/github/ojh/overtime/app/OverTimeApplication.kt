package com.github.ojh.overtime.app

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import io.fabric.sdk.android.Fabric
import io.realm.Realm
import io.realm.RealmConfiguration


class OverTimeApplication : Application() {

    companion object {
        @JvmStatic
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        initRealmConfiguration()

        Fabric.with(this, Crashlytics())

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

    private fun initRealmConfiguration() {
        Realm.init(this)

        val config = RealmConfiguration.Builder()
                .name("overtime")
                .schemaVersion(1)
//                .migration { mi, oldVersion, newVersion ->
//                    var targetVersion = oldVersion
//
//                    if (targetVersion == 0L) {
//                        mi.schema.get("TimeLine")
//                                .addField("imgUrl", String::class.java)
//                    }
//                }
//                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(config)
    }
}