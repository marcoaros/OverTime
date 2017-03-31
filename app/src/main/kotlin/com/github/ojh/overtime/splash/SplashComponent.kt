package com.github.ojh.overtime.splash

import com.github.ojh.overtime.base.ActivityComponent
import com.github.ojh.overtime.base.scope.PerActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(
        modules = arrayOf(SplashModule::class)
)
interface SplashComponent : ActivityComponent {
    fun inject(splashActivity: SplashActivity)
}