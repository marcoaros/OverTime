package com.github.ojh.overtime.splash

import com.github.ojh.overtime.base.ActivityComponent
import com.github.ojh.overtime.app.AppComponent
import com.github.ojh.overtime.base.scope.PerActivity
import dagger.Component

@PerActivity
@Component(
        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(SplashModule::class)
)
interface SplashComponent : ActivityComponent {
    fun inject(splashActivity: SplashActivity)
}