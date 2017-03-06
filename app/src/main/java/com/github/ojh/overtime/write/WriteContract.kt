package com.github.ojh.overtime.write

import com.github.ojh.overtime.base.BaseContract

/**
 * Created by ohjaehwan on 2017. 3. 6..
 */
interface WriteContract {
    interface View : BaseContract.View {
        fun initView()
    }

    interface Presenter<V: View>: BaseContract.Presenter<V> {
        fun init()
        fun clickSave()
    }
}