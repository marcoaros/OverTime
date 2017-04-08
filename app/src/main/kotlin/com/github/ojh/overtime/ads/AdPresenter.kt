package com.github.ojh.overtime.ads

import com.github.ojh.overtime.base.BasePresenter
import com.google.android.gms.ads.AdRequest
import javax.inject.Inject

class AdPresenter<V : AdContract.View> @Inject constructor()
    : BasePresenter<V>(), AdContract.Presenter<V> {

    override fun getAd() {
        getView()?.setAdView(AdRequest.Builder().build())
    }
}