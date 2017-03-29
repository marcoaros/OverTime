package com.github.ojh.overtime.main.calendar

import com.github.ojh.overtime.base.scope.PerFragment
import dagger.Module
import dagger.Provides

@Module
class CalendarModule {

    @PerFragment
    @Provides
    fun provideCalendarPresenter(
            calendarPresenter: CalendarPresenter<CalendarContract.View>

    ): CalendarContract.Presenter<CalendarContract.View> = calendarPresenter
}