package com.github.ojh.overtime.di

import android.app.Application
import com.github.ojh.overtime.data.DataSource
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

/**
 * Created by ohjaehwan on 2017. 2. 27..
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun application(): Application
    fun dataSource(): DataSource
    fun compositeDisables(): CompositeDisposable
}