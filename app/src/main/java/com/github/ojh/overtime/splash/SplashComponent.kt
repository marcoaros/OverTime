package com.github.ojh.overtime.splash

import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.di.PerActivity
import dagger.Component

/**
 * Created by ohjaehwan on 2017. 2. 27..
 */
@PerActivity
@Component(dependencies = arrayOf(AppComponent::class))
interface SplashComponent : SplashContract.Component<SplashContract.View, SplashPresenter> {
    override fun presenter(): SplashPresenter
}