package com.github.ojh.overtime.splash

import com.github.ojh.overtime.base.BaseContract

/**
 * Created by ohjaehwan on 2017. 2. 27..
 */
interface SplashContract {

    interface View : BaseContract.View {
        fun showToast(message: String)
    }

    interface Presenter<V : View> : BaseContract.Presenter<V> {
        fun clickButton()
    }

    interface Component<V : View, out P : Presenter<V>> : BaseContract.Component<V, P>
}