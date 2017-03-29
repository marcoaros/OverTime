package com.github.ojh.overtime.main.calendar

import android.view.View
import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.data.Events
import com.github.ojh.overtime.util.EventBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class CalendarPresenter<V : CalendarContract.View> @Inject constructor(
        dataManager: DataManager,
        compositeDisposable: CompositeDisposable

) : BasePresenter<V>(dataManager, compositeDisposable), CalendarContract.Presenter<V> {

    override fun initWrittenDates() {
        compositeDisposable.add(
                dataManager.getWrittenDates()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            getView()?.setWrittenDate(it)
                        }
        )
    }

    override fun initEventBus() {
        compositeDisposable.add(EventBus.asFlowable()
                .subscribe {
                    when (it) {
                        is Events.WriteEvent -> {
                            getView()?.setWrittenDate(listOf(it.timeLine.mDate).filterNotNull())
                        }

                        is Events.DeleteEvent -> {
                            initWrittenDates()
                        }
                    }
                }
        )
    }

    override fun onSelectDate(date: Date?, view: View?) {
        getView()?.navigateToList(date)
    }

}