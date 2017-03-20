package com.github.ojh.overtime.timeline

import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.data.model.TimeLine
import com.github.ojh.overtime.timeline.adapter.TimeLineAdapterContract
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

    override fun initEventListener() {
        timeLineAdapterView.setOnClickViewHolder { view, position ->
            val timeLineId = timeLineAdapterModel.findTimeLineId(position)
            timeLineId?.let {
                getView()?.navigateToDetail(view, timeLineId)
            }
        }

        timeLineAdapterView.setOnLongClickViewHolder { view, position ->
            val timeLineId = timeLineAdapterModel.findTimeLineId(position)
            timeLineId?.let {
                getView()?.navigateToSetting(it)
            }

        }
    }

    override fun getTimeLines() {
        compositeDisposable.add(
                dataManager.getTimeLines()
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

    override fun addTimeLine(timeLine: TimeLine, position: Int) {
        timeLineAdapterModel.addTimeLine(timeLine, position)
        getView()?.scrollToPosition(position)
    }

    override fun updateTimeLine(timeLine: TimeLine) {
        timeLineAdapterModel.updateTimeLine(timeLine)
    }

    override fun deleteTimeLine(timeLineId: Int) {
        timeLineAdapterModel.deleteTimeLine(timeLineId)
    }
}