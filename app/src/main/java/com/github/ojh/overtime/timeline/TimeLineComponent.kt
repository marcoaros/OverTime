package com.github.ojh.overtime.timeline

import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.di.PerActivity
import dagger.Component

/**
 * Created by OhJaeHwan on 2017-02-28.
 */
@PerActivity
@Component(
        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(TimeLineModule::class)
)
interface TimeLineComponent {
    fun inject(timeLineActivity: TimeLineActivity)
}