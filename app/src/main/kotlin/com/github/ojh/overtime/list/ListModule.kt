package com.github.ojh.overtime.main

import android.app.Application
import android.widget.ArrayAdapter
import com.github.ojh.overtime.R
import com.github.ojh.overtime.base.scope.PerActivity
import com.github.ojh.overtime.main.timeline.adapter.TimeLineAdapter
import com.github.ojh.overtime.main.timeline.adapter.TimeLineAdapterContract
import dagger.Module
import dagger.Provides

@Module
class ListModule(val timeLineAdapter: TimeLineAdapter) {

    @PerActivity
    @Provides
    fun provideListPresenter(
            listPresenter: ListPresenter<ListContract.View>

    ): ListContract.Presenter<ListContract.View> = listPresenter

    @PerActivity
    @Provides
    fun provideTimeLineAdapterModel(): TimeLineAdapterContract.Model = timeLineAdapter

    @PerActivity
    @Provides
    fun provideTimeLineAdapterView(): TimeLineAdapterContract.View = timeLineAdapter

    @PerActivity
    @Provides
    fun provideFilterArrayAdapter(application: Application): ArrayAdapter<CharSequence> {
        val adapter = ArrayAdapter.createFromResource(application, R.array.filter_array, R.layout.spiiner_item)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        return adapter
    }
}
