package com.github.ojh.overtime.main.timeline

import com.github.ojh.overtime.base.scope.PerFragment
import dagger.Subcomponent

@PerFragment
@Subcomponent(
        modules = arrayOf(TimeLineModule::class)
)
interface TimeLineComponent {
    fun inject(timeLineFragment: TimeLineFragment)
}