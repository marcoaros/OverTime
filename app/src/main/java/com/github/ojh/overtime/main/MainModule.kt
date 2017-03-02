package com.github.ojh.overtime.main

import com.github.ojh.overtime.di.PerActivity
import dagger.Module
import dagger.Provides

/**
 * Created by ohjaehwan on 2017. 3. 2..
 */
@Module
class MainModule {

    @PerActivity
    @Provides
    fun provideMainPresenter(mainPresenter: MainPresenter<MainContract.View>): MainContract.Presenter<MainContract.View> = mainPresenter
}