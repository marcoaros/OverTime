package com.github.ojh.overtime.main.setting.backup

import com.github.ojh.overtime.base.BaseContract

interface BackUpContract {
    interface View : BaseContract.View {
        fun setBackUpRecyclerView(pathList: List<String>)
        fun showRestoreResult(message: String)
    }

    interface Presenter<V : View> : BaseContract.Presenter<V> {
        fun loadBackUpFilePaths()
        fun restoreData(internalFilePath: String, exportFilePath: String)
    }
}