package com.github.ojh.overtime.main.timeline

import com.github.ojh.overtime.di.PerFragment
import com.github.ojh.overtime.main.timeline.adapter.TimeLineAdapter
import com.github.ojh.overtime.main.timeline.adapter.TimeLineAdapterContract
import dagger.Module
import dagger.Provides

@Module
class TimeLineModule(val timeLineAdapter: TimeLineAdapter) {

    @PerFragment
    @Provides
    fun provideMainPresenter(
            timeLinePresenter: TimeLinePresenter<TimeLineContract.View>

    ): TimeLineContract.Presenter<TimeLineContract.View> = timeLinePresenter

    @PerFragment
    @Provides
    fun provideTimeLineAdapterModel(): TimeLineAdapterContract.Model = timeLineAdapter

    @PerFragment
    @Provides
    fun provideTimeLineAdapterView(): TimeLineAdapterContract.View = timeLineAdapter
}