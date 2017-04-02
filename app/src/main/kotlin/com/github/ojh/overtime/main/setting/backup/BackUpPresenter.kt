package com.github.ojh.overtime.main.setting.backup

import android.app.Application
import android.os.Environment
import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import javax.inject.Inject

class BackUpPresenter<V : BackUpContract.View> @Inject constructor(
        private val application: Application,
        private val dataManager: DataManager

) : BasePresenter<V>(), BackUpContract.Presenter<V> {
    override fun loadBackUpFilePaths() {

        val exportRealmPATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        val backUpFileList = exportRealmPATH.listFiles()
                .filter {
                    it.name.contains("realm")
                }
                .map {
                    it.path
                }

        getView()?.setBackUpRecyclerView(backUpFileList)
    }

    override fun restoreData(internalFilePath: String, exportFilePath: String) {
        compositeDisposable.add(
                dataManager.restoreData(internalFilePath, exportFilePath)
                        .subscribe {
                            getView()?.showRestoreResult(it)
                        }
        )
    }
}