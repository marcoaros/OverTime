package com.github.ojh.overtime.ads

import com.github.ojh.overtime.base.BaseContract
import com.google.android.gms.ads.AdRequest

interface AdContract {
    interface View: BaseContract.View {
        fun setAdView(adRequest: AdRequest)
    }

    interface Presenter<V: View>: BaseContract.Presenter<V> {
        fun getAd()
    }
}