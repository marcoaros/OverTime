package com.github.ojh.overtime.main.timeline

import android.app.Application
import android.widget.ArrayAdapter
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.scope.PerFragment
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

    @PerFragment
    @Provides
    fun provideFilterArrayAdapter(application: Application): ArrayAdapter<CharSequence> {
        val adapter = ArrayAdapter.createFromResource(application, R.array.filter_array, R.layout.spiiner_item)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        return adapter
    }
}