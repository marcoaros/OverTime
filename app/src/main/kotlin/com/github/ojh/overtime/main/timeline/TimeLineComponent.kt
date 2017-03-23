package com.github.ojh.overtime.main.timeline

import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.di.PerFragment
import com.github.ojh.overtime.main.timeline.TimeLineFragment
import dagger.Component

@PerFragment
@Component(
        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(TimeLineModule::class)
)
interface TimeLineComponent {
    fun inject(timeLineFragment: TimeLineFragment)
}