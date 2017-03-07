package com.github.ojh.overtime

import android.app.Application
import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.di.AppModule
import com.github.ojh.overtime.di.DaggerAppComponent
import io.realm.Realm


/**
 * Created by ohjaehwan on 2017. 2. 27..
 */
class OverTimeApplication : Application() {

    companion object {
        @JvmStatic
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        initRealmConfiguration()

    }

    private fun initRealmConfiguration() {
        Realm.init(this)
    }
}