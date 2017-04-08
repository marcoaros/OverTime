package com.github.ojh.overtime.main.calendar

import com.github.ojh.overtime.base.scope.PerFragment
import dagger.Subcomponent

@PerFragment
@Subcomponent(
        modules = arrayOf(CalendarModule::class)
)
interface CalendarComponent {
    fun inject(calendarFragment: CalendarFragment)
}