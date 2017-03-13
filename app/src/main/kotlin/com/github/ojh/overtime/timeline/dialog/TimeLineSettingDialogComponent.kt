package com.github.ojh.overtime.timeline.dialog

import com.github.ojh.overtime.di.AppComponent
import com.github.ojh.overtime.di.PerFragment
import dagger.Component

@PerFragment
@Component(
        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(TimeLineSettingDialogModule::class)
)
interface TimeLineSettingDialogComponent {
    fun inject(timeLineSettingDialog: TimeLineSettingDialog)
}