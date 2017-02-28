package com.github.ojh.overtime.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by ohjaehwan on 2017. 2. 27..
 */
@Module
class AppModule(val application: Application) {
    @Singleton
    @Provides
    fun providesApplication(): Application = application
}