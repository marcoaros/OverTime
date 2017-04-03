package com.github.ojh.overtime.main.setting.backup

import android.app.Application
import android.os.Environment
import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RestorePresenter<V : RestoreContract.View> @Inject constructor(
        private val application: Application,
        private val dataManager: DataManager

) : BasePresenter<V>(), RestoreContract.Presenter<V> {
    override fun loadBackUpFilePaths() {

        val exportRealmPATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        val backUpFileList = exportRealmPATH.listFiles()
                .filter {
                    it.name.contains(".realm")
                }
                .map {
                    it.path
                }

        getView()?.setRecyclerView(backUpFileList)
    }

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