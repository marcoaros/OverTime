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
        dataManager: DataManager,
        compositeDisposable: CompositeDisposable,
        val timeLineAdapterModel: TimeLineAdapterContract.Model,
        val timeLineAdapterView: TimeLineAdapterContract.View
) : BasePresenter<V>(dataManager, compositeDisposable), TimeLineContract.Presenter<V> {

    override fun clickFabWrite() {
        getView()?.showWriteDialog()
    }

    override fun getTimeLines() {
        compositeDisposable.add(
                dataManager.getTimeLineData()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete { timeLineAdapterView.refreshAll() }
                        .subscribe { timeLines ->
                            timeLineAdapterModel.setTimeLines(timeLines)
                        }
        )
    }

    override fun addTimeLine(timeLine: TimeLine, position: Int) {
        timeLineAdapterModel.addTimeLine(timeLine, position)
    }
}