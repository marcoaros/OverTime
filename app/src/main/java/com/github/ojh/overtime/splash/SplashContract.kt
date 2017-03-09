package com.github.ojh.overtime.splash

import com.github.ojh.overtime.base.BaseContract

/**
 * Created by ohjaehwan on 2017. 2. 27..
 */
interface SplashContract {
    interface View : BaseContract.View {
        fun navigateToTimeLine()
    }
    interface Presenter<V : View> : BaseContract.Presenter<V> {
        fun init()
    }
}