package com.github.ojh.overtime.timeline

import android.app.Application
import android.widget.ArrayAdapter
import com.github.ojh.overtime.R
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

    @PerActivity
    @Provides
    fun provideFilterArrayAdapter(application: Application): ArrayAdapter<CharSequence> {
        val adapter = ArrayAdapter.createFromResource(application, R.array.filter_array, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        return adapter
    }
}