package com.github.ojh.overtime.base

import com.github.ojh.overtime.detail.DetailComponent
import com.github.ojh.overtime.detail.DetailModule
import com.github.ojh.overtime.edit.EditComponent
import com.github.ojh.overtime.edit.EditModule
import com.github.ojh.overtime.main.ListComponent
import com.github.ojh.overtime.main.ListModule
import com.github.ojh.overtime.main.MainComponent
import com.github.ojh.overtime.main.MainModule
import com.github.ojh.overtime.main.setting.restore.RestoreComponent
import com.github.ojh.overtime.main.setting.restore.RestoreModule
import com.github.ojh.overtime.splash.SplashComponent
import com.github.ojh.overtime.splash.SplashModule
import com.github.ojh.overtime.write.WriteComponent
import com.github.ojh.overtime.write.WriteModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(overTimeApplication: OverTimeApplication)

    fun plus(splashModule: SplashModule): SplashComponent
    fun plus(mainModule: MainModule): MainComponent
    fun plus(writeModule: WriteModule): WriteComponent
    fun plus(detailModule: DetailModule): DetailComponent
    fun plus(editModule: EditModule): EditComponent
    fun plus(listModule: ListModule): ListComponent
    fun plus(restoreModule: RestoreModule): RestoreComponent
}