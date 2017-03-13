package com.github.ojh.overtime.timeline.dialog

import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class TimeLineSettingDialogPresenter<V : TimeLineSettingDialogContract.View> @Inject constructor(
        dataManager: DataManager,
        compositeDisposable: CompositeDisposable
) : BasePresenter<V>(dataManager, compositeDisposable), TimeLineSettingDialogContract.Presenter<V> {

    override fun clickUpdate() {

    }

    override fun clickDelete() {

    }

    override fun clickCancel() {

    }
}