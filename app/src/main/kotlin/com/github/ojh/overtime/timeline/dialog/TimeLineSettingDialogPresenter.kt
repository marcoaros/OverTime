package com.github.ojh.overtime.timeline.dialog

import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.data.model.Events
import com.github.ojh.overtime.util.EventBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.properties.Delegates

class TimeLineSettingDialogPresenter<V : TimeLineSettingDialogContract.View> @Inject constructor(
        dataManager: DataManager,
        compositeDisposable: CompositeDisposable
) : BasePresenter<V>(dataManager, compositeDisposable), TimeLineSettingDialogContract.Presenter<V> {

    var timeLineId: Int by Delegates.notNull<Int>()

    override fun init(timeLineId: Int) {
        this.timeLineId = timeLineId
    }

    override fun clickUpdate() {
        compositeDisposable.add(
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

    override fun clickDelete() {
        dataManager.deleteTimeLine(timeLineId)
        getView()?.dismissDialog()
        EventBus.post(Events.DeleteEvent(timeLineId))
    }

    override fun clickCancel() {
        getView()?.dismissDialog()
    }
}