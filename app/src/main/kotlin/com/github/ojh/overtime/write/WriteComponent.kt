package com.github.ojh.overtime.write

import com.github.ojh.overtime.base.ActivityComponent
import com.github.ojh.overtime.base.scope.PerActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(
        modules = arrayOf(WriteModule::class)
)
interface WriteComponent : ActivityComponent {
    fun inject(writeActivity: WriteActivity)
}