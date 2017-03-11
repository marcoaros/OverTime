package com.github.ojh.overtime.splash

import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.di.PerActivity
import dagger.Component

@PerActivity
@Component(
        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(SplashModule::class)
)
interface SplashComponent {
    fun inject(splashActivity: SplashActivity)
}