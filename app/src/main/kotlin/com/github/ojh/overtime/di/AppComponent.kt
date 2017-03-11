package com.github.ojh.overtime.di

import android.app.Application
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.data.DataSource
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun application(): Application
    fun dataManager(): DataManager
    fun compositeDisables(): CompositeDisposable
}