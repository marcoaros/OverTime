package com.github.ojh.overtime.main.timeline

import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.data.TimeLine
import com.github.ojh.overtime.main.timeline.adapter.TimeLineAdapterContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.properties.Delegates

class TimeLinePresenter<V : TimeLineContract.View> @Inject constructor(
        private val timeLineAdapterModel: TimeLineAdapterContract.Model,
        private val timeLineAdapterView: TimeLineAdapterContract.View,
        dataManager: DataManager,
        compositeDisposable: CompositeDisposable

) : BasePresenter<V>(dataManager, compositeDisposable), TimeLineContract.Presenter<V> {

    private var filter by Delegates.notNull<Int>()

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

    override fun clickWrite() {
        getView()?.navigateToWrite()
    }

    override fun getTimeLines(filter: Int) {
        this.filter = filter

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

    override fun addTimeLine(timeLine: TimeLine) {
        val insertedPosition = if(filter == 1) {
            timeLineAdapterModel.getSize()
        } else {
            0
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