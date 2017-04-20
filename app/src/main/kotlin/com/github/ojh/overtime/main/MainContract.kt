package com.github.ojh.overtime.main

import com.github.ojh.overtime.base.BaseContract
import com.google.android.gms.ads.AdRequest

interface MainContract {
    interface View: BaseContract.View {
        fun navigateToWrite()
        fun showPinDialog()
        fun initAdView(adRequest: AdRequest)
        fun dismissAdView()
        fun showAdView()
    }

    interface Presenter<V: MainContract.View>: BaseContract.Presenter<V> {
        fun getPinSettings()
        fun clickWriteButton()
        fun clickAdCancel()
        fun clickAdClose()
        fun onBackPress()
        fun initAd()
    }
}