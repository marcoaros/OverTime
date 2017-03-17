package com.github.ojh.overtime.timeline.list

import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.data.model.TimeLine
import com.github.ojh.overtime.timeline.list.adapter.TimeLineAdapterContract
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

    override fun clickFabWrite() {
        getView()?.showWriteDialog()
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
    }

    override fun updateTimeLine(timeLine: TimeLine) {
        timeLineAdapterModel.updateTimeLine(timeLine)
    }

    override fun deleteTimeLine(timeLineId: Int) {
        timeLineAdapterModel.deleteTimeLine(timeLineId)
    }
}