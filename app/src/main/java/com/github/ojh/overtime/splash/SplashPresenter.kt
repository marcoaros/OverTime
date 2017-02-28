package com.github.ojh.overtime.splash

import com.github.ojh.overtime.base.BasePresenter
import javax.inject.Inject

/**
 * Created by ohjaehwan on 2017. 2. 27..
 */
class SplashPresenter @Inject constructor()
    : BasePresenter<SplashContract.View>(), SplashContract.Presenter<SplashContract.View> {

    override fun clickButton() {
        getView().showToast("wow")
    }

}