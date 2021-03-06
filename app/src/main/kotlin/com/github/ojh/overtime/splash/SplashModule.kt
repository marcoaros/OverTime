package com.github.ojh.overtime.splash

import com.github.ojh.overtime.base.scope.PerActivity
import dagger.Module
import dagger.Provides

@Module
class SplashModule {

    @PerActivity
    @Provides
    fun provideSplashPresenter(
            splashPresenter: SplashPresenter<SplashContract.View>

    ): SplashContract.Presenter<SplashContract.View> = splashPresenter
}