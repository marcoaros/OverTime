package com.github.ojh.overtime.edit

import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.data.Events
import com.github.ojh.overtime.util.EventBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.properties.Delegates

class EditPresenter<V : EditContract.View> @Inject constructor(
        private val dataManager: DataManager

) : BasePresenter<V>(), EditContract.Presenter<V> {

    private var timeLineId: Int by Delegates.notNull<Int>()

    override fun init(timeLineId: Int) {
        this.timeLineId = timeLineId
    }

    override fun updateTimeLine() {
        addDisposable(
                dataManager.getTimeLine(timeLineId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete {
                            getView()?.dismissDialog()
                        }
                        .subscribe {
                            getView()?.navigateToWrite(it)
                        }
        )
    }

    override fun deleteTimeLine() {
        dataManager.deleteTimeLine(timeLineId)
        EventBus.post(Events.DeleteEvent(timeLineId))
        getView()?.dismissDialog()
    }

    override fun dismiss() {
        getView()?.dismissDialog()
    }
}