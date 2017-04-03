package com.github.ojh.overtime.main.setting.backup

import com.github.ojh.overtime.base.BaseContract

interface RestoreContract {
    interface View : BaseContract.View {
        fun setRecyclerView(pathList: List<String>)
        fun showRestoreResult(message: String)

        fun showProgress()
        fun dismissProgress()
    }

    interface Presenter<V : View> : BaseContract.Presenter<V> {
        fun loadBackUpFilePaths()
        fun restoreData(internalFilePath: String, exportFilePath: String)
    }
}