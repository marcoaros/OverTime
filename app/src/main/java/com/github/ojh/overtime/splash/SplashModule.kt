package com.github.ojh.overtime.splash

import com.github.ojh.overtime.di.PerActivity
import dagger.Module
import dagger.Provides

/**
 * Created by ohjaehwan on 2017. 3. 2..
 */
@Module
class SplashModule {

    @PerActivity
    @Provides
    fun provideSplashPresenter(splashPresenter: SplashPresenter<SplashContract.View>): SplashContract.Presenter<SplashContract.View> {
        return splashPresenter
    }
}