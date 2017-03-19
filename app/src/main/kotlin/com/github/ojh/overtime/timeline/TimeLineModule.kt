package com.github.ojh.overtime.timeline

import com.github.ojh.overtime.di.PerActivity
import com.github.ojh.overtime.timeline.adapter.TimeLineAdapter
import com.github.ojh.overtime.timeline.adapter.TimeLineAdapterContract
import dagger.Module
import dagger.Provides

@Module
class TimeLineModule(val timeLineAdapter: TimeLineAdapter) {

    @PerActivity
    @Provides
    fun provideMainPresenter(
            timeLinePresenter: TimeLinePresenter<TimeLineContract.View>

    ): TimeLineContract.Presenter<TimeLineContract.View> = timeLinePresenter

    @PerActivity
    @Provides
    fun provideTimeLineAdapterModel(): TimeLineAdapterContract.Model = timeLineAdapter

    @PerActivity
    @Provides
    fun provideTimeLineAdapterView(): TimeLineAdapterContract.View = timeLineAdapter
}