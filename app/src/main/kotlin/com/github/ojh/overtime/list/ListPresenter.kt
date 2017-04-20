package com.github.ojh.overtime.main

import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.data.Events
import com.github.ojh.overtime.data.FilterEqualDate
import com.github.ojh.overtime.data.TimeLine
import com.github.ojh.overtime.main.timeline.adapter.TimeLineAdapterContract
import com.github.ojh.overtime.util.EventBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class ListPresenter<V: ListContract.View> @Inject constructor(
        private val timeLineAdapterModel: TimeLineAdapterContract.Model,
        private val timeLineAdapterView: TimeLineAdapterContract.View,
        private val dataManager: DataManager

) : BasePresenter<V>(), ListContract.Presenter<V> {

    private lateinit var date: Date

    init {
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

        addDisposable(EventBus.asFlowable()
                .subscribe {
                    when (it) {
                        is Events.WriteEvent -> subscribeAddTimeLine(it.timeLine)
                        is Events.UpdateEvent -> subsribeUpdateTimeLine(it.timeLine)
                        is Events.DeleteEvent -> subscribeDeleteTimeLine(it.timeLineId)
                    }
                }
        )
    }

    override fun initDate(date: Date) {
        this.date = date
    }


    override fun getTimeLines() {
        addDisposable(
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

    override fun subscribeAddTimeLine(timeLine: TimeLine) {
        timeLineAdapterModel.addTimeLine(timeLine, 0)
        getView()?.scrollToPosition(0)
    }

    override fun subsribeUpdateTimeLine(timeLine: TimeLine) {
        timeLineAdapterModel.updateTimeLine(timeLine)
    }

    override fun subscribeDeleteTimeLine(timeLineId: Int) {
        timeLineAdapterModel.deleteTimeLine(timeLineId)
    }
}