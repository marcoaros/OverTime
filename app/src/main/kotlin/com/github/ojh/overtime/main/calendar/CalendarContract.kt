package com.github.ojh.overtime.main.calendar

import com.github.ojh.overtime.base.BaseContract
import java.util.*

interface CalendarContract {
    interface View : BaseContract.View {
        fun setWrittenDate(dateList: List<Date>)
    }

    interface Presenter<V: BaseContract.View>: BaseContract.Presenter<V> {
        fun initWrittenDates()
        fun initEventListener()
    }
}