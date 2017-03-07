package com.github.ojh.overtime.write

import com.github.ojh.overtime.base.BaseContract
import com.github.ojh.overtime.data.model.TimeLine

/**
 * Created by ohjaehwan on 2017. 3. 6..
 */
interface WriteContract {
    interface View : BaseContract.View {
        fun initView()
        fun setErrorContent(isError: Boolean)
        fun navigateToMain()
    }

    interface Presenter<V : View> : BaseContract.Presenter<V> {
        fun init()
        fun clickSave(timeLine: TimeLine)
        fun validateContent(content: String?)
    }
}