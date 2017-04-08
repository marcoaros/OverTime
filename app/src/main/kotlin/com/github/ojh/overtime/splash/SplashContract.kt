package com.github.ojh.overtime.splash

import com.airbnb.lottie.LottieAnimationView
import com.github.ojh.overtime.base.BaseContract

interface SplashContract {
    interface View : BaseContract.View {
        fun navigateToTimeLine()
    }
    interface Presenter<V : View> : BaseContract.Presenter<V> {
        fun init(views: Array<LottieAnimationView>)
    }
}