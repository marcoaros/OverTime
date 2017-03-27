package com.github.ojh.overtime.write

import com.github.ojh.overtime.base.ActivityComponent
import com.github.ojh.overtime.app.AppComponent
import com.github.ojh.overtime.base.scope.PerActivity
import dagger.Component

@PerActivity
@Component(
        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(WriteModule::class)
)
interface WriteComponent : ActivityComponent {
    fun inject(writeActivity: WriteActivity)
}