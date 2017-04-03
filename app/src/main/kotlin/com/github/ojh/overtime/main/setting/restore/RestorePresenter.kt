package com.github.ojh.overtime.main.setting.restore

import android.os.Environment
import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RestorePresenter<V : RestoreContract.View> @Inject constructor(
        private val dataManager: DataManager

) : BasePresenter<V>(), RestoreContract.Presenter<V> {

    override fun restoreData(internalFilePath: String, exportFilePath: String) {
        getView()?.showProgress()

        compositeDisposable.add(
                dataManager.restoreData(internalFilePath, exportFilePath)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete {
                            getView()?.dismissProgress()
                        }
                        .subscribe {
                            getView()?.showRestoreResult(it)
                        }
        )
    }
}