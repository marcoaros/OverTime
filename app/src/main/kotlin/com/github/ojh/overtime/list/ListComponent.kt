package com.github.ojh.overtime.main

import com.github.ojh.overtime.app.AppComponent
import com.github.ojh.overtime.base.ActivityComponent
import com.github.ojh.overtime.base.scope.PerActivity
import com.github.ojh.overtime.list.ListActivity
import dagger.Component

@PerActivity
@Component(
        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(ListModule::class)
)
interface ListComponent : ActivityComponent{
    fun inject(listActivity: ListActivity)
}