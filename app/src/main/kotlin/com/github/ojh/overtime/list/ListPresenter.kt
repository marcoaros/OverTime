package com.github.ojh.overtime.main

import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.data.Events
import com.github.ojh.overtime.data.FilterEqualDate
import com.github.ojh.overtime.data.TimeLine
import com.github.ojh.overtime.main.timeline.adapter.TimeLineAdapterContract
import com.github.ojh.overtime.util.EventBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class ListPresenter<V: ListContract.View> @Inject constructor(
        private val timeLineAdapterModel: TimeLineAdapterContract.Model,
        private val timeLineAdapterView: TimeLineAdapterContract.View,
        private val dataManager: DataManager

) : BasePresenter<V>(), ListContract.Presenter<V> {

    private lateinit var date: Date

    override fun initDate(date: Date) {
        this.date = date
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

        compositeDisposable.add(EventBus.asFlowable()
                .subscribe {
                    when (it) {
                        is Events.WriteEvent -> addTimeLine(it.timeLine)
                        is Events.UpdateEvent -> updateTimeLine(it.timeLine)
                        is Events.DeleteEvent -> deleteTimeLine(it.timeLineId)
                    }
                }
        )
    }

    override fun getTimeLines() {
        compositeDisposable.add(
                dataManager.getTimeLines(FilterEqualDate(date))
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

    override fun addTimeLine(timeLine: TimeLine) {
        timeLineAdapterModel.addTimeLine(timeLine, 0)
        getView()?.scrollToPosition(0)
    }

    override fun updateTimeLine(timeLine: TimeLine) {
        timeLineAdapterModel.updateTimeLine(timeLine)
    }

    override fun deleteTimeLine(timeLineId: Int) {
        timeLineAdapterModel.deleteTimeLine(timeLineId)
    }
}