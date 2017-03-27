package com.github.ojh.overtime.main.timeline

import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.data.TimeLine
import com.github.ojh.overtime.main.timeline.adapter.TimeLineAdapterContract
import com.github.ojh.overtime.util.FilterDateAscending
import com.github.ojh.overtime.util.FilterDateDescending
import com.github.ojh.overtime.util.FilterType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TimeLinePresenter<V : TimeLineContract.View> @Inject constructor(
        private val timeLineAdapterModel: TimeLineAdapterContract.Model,
        private val timeLineAdapterView: TimeLineAdapterContract.View,
        dataManager: DataManager,
        compositeDisposable: CompositeDisposable

) : BasePresenter<V>(dataManager, compositeDisposable), TimeLineContract.Presenter<V> {

    companion object {
        private var filterType: FilterType = FilterDateDescending()
    }

    override fun initEventListener() {
        timeLineAdapterView.setOnClickViewHolder { view, position ->
            val timeLineId = timeLineAdapterModel.findTimeLineId(position)
            timeLineId?.let {
                getView()?.navigateToDetail(view, timeLineId)
            }
        }

        timeLineAdapterView.setOnClickSetting { _, position ->
            val timeLineId = timeLineAdapterModel.findTimeLineId(position)
            timeLineId?.let {
                getView()?.navigateToSetting(it)
            }

        }
    }

    override fun getTimeLines(filter: FilterType) {
        filterType = filter

        compositeDisposable.add(
                dataManager.getTimeLines(filter)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete {
                            timeLineAdapterView.refreshAll()
                        }
                        .subscribe {
                            timeLineAdapterModel.setTimeLines(it)
                        }
        )
    }

    override fun getItemCount(): Int = timeLineAdapterModel.getSize()

    override fun getFilterType(): FilterType {
        return filterType
    }

    override fun addTimeLine(timeLine: TimeLine) {
        val insertedPosition = when(filterType) {
            is FilterDateDescending -> 0
            is FilterDateAscending -> timeLineAdapterModel.getSize()
        }

        timeLineAdapterModel.addTimeLine(timeLine, insertedPosition)
        getView()?.scrollToPosition(insertedPosition)
    }

    override fun updateTimeLine(timeLine: TimeLine) {
        timeLineAdapterModel.updateTimeLine(timeLine)
    }

    override fun deleteTimeLine(timeLineId: Int) {
        timeLineAdapterModel.deleteTimeLine(timeLineId)
    }
}