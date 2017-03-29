package com.github.ojh.overtime.main

import com.github.ojh.overtime.app.AppComponent
import com.github.ojh.overtime.base.ActivityComponent
import com.github.ojh.overtime.base.scope.PerActivity
import com.github.ojh.overtime.edit.EditComponent
import com.github.ojh.overtime.edit.EditModule
import com.github.ojh.overtime.main.calendar.CalendarComponent
import com.github.ojh.overtime.main.calendar.CalendarModule
import com.github.ojh.overtime.main.setting.SettingComponent
import com.github.ojh.overtime.main.setting.SettingModule
import com.github.ojh.overtime.main.timeline.TimeLineComponent
import com.github.ojh.overtime.main.timeline.TimeLineModule
import dagger.Component

@PerActivity
@Component(
        dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(MainModule::class)
)
interface MainComponent : ActivityComponent {
    fun inject(mainActivity: MainActivity)
    fun plus(timeLineModule: TimeLineModule): TimeLineComponent
    fun plus(editModule: EditModule): EditComponent
    fun plus(settingModule: SettingModule): SettingComponent
    fun plus(calendarModule: CalendarModule): CalendarComponent
}