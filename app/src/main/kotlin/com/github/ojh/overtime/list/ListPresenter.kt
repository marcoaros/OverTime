package com.github.ojh.overtime.main

import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.data.FilterEqualDate
import com.github.ojh.overtime.main.timeline.adapter.TimeLineAdapterContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class ListPresenter<V: ListContract.View> @Inject constructor(
        private val timeLineAdapterModel: TimeLineAdapterContract.Model,
        private val timeLineAdapterView: TimeLineAdapterContract.View,
        dataManager: DataManager,
        compositeDisposable: CompositeDisposable

) : BasePresenter<V>(dataManager, compositeDisposable), ListContract.Presenter<V> {

    private lateinit var date: Date

    override fun initDate(date: Date) {
        this.date = date
    }

    override fun initEventListener() {

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
}