package com.github.ojh.overtime.main.setting.restore

import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.data.Events
import com.github.ojh.overtime.util.EventBus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RestorePresenter<V : RestoreContract.View> @Inject constructor(
        private val dataManager: DataManager

) : BasePresenter<V>(), RestoreContract.Presenter<V> {

    override fun restoreData(internalFilePath: String, externalFilePath: String) {
        getView()?.showProgress()

        addDisposable(
                dataManager.restoreData(internalFilePath, externalFilePath)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete {
                            EventBus.post(Events.RefreshEvent())
                            getView()?.dismissProgress()
                        }
                        .subscribe {
                            getView()?.showRestoreResult(it)
                        }
        )
    }
}