package com.github.ojh.overtime.di

import android.app.Application
import com.github.ojh.overtime.data.DataManager
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

/**
 * Created by ohjaehwan on 2017. 2. 27..
 */
@Module
class AppModule(val application: Application) {
    @Singleton
    @Provides
    fun providesApplication(): Application = application

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Singleton
    @Provides
    fun provideDataManager(): DataManager = DataManager()
}