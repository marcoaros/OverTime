package com.github.ojh.overtime.write

import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.di.PerActivity
import dagger.Component

/**
 * Created by ohjaehwan on 2017. 3. 6..
 */
@PerActivity
@Component(
        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(WriteModule::class)
)
interface WriteComponent {
    fun inject(writeActivity: WriteActivity)
}