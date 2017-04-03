package com.github.ojh.overtime.main.setting.restore

import com.github.ojh.overtime.base.BaseContract

interface RestoreContract {
    interface View : BaseContract.View {
        fun showProgress()
        fun dismissProgress()
        fun showRestoreResult(message: String)
    }

    interface Presenter<V : View> : BaseContract.Presenter<V> {
        fun restoreData(internalFilePath: String, exportFilePath: String)
    }
}