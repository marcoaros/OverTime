package com.github.ojh.overtime.main

import com.github.ojh.overtime.base.ActivityComponent
import com.github.ojh.overtime.base.scope.PerActivity
import com.github.ojh.overtime.list.ListActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(
        modules = arrayOf(ListModule::class)
)
interface ListComponent : ActivityComponent{
    fun inject(listActivity: ListActivity)
}