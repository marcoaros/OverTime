package com.github.ojh.overtime.main

import com.github.ojh.overtime.base.scope.PerActivity
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @PerActivity
    @Provides
    fun provideMainPresenter(
            mainPresenter: MainPresenter<MainContract.View>

    ): MainContract.Presenter<MainContract.View> = mainPresenter
}
