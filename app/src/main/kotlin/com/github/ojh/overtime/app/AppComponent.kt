package com.github.ojh.overtime.app

import android.app.Application
import com.github.ojh.overtime.api.FirebaseAPI
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.util.PropertyUtil
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun application(): Application
    fun dataManager(): DataManager
    fun compositeDisables(): CompositeDisposable
    fun firebaseApi(): FirebaseAPI
    fun propertyUtil(): PropertyUtil
}