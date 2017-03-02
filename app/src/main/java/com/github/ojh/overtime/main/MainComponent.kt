package com.github.ojh.overtime.main

import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.di.PerActivity
import dagger.Component

/**
 * Created by OhJaeHwan on 2017-02-28.
 */
@PerActivity
@Component(
        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(MainModule::class)
)
interface MainComponent  {
    fun inject(mainActivity: MainActivity)
}