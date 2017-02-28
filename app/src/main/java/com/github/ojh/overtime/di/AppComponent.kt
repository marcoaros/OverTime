package com.github.ojh.overtime.di

import android.app.Application
import dagger.Component
import javax.inject.Singleton

/**
 * Created by ohjaehwan on 2017. 2. 27..
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun aplication(): Application
}