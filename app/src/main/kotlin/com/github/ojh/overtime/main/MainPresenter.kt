package com.github.ojh.overtime.main

import android.app.Activity
import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.util.BackPressCloseHandler
import com.github.ojh.overtime.util.PropertyManager
import com.google.android.gms.ads.AdRequest
import javax.inject.Inject

class MainPresenter<V : MainContract.View> @Inject constructor(
        private val propertyManager: PropertyManager,
        private val backPressCloseHandler: BackPressCloseHandler

) : BasePresenter<V>(), MainContract.Presenter<V> {

    companion object {
        const val KEY_PIN = "key_pin"
    }

    override fun getPinSettings() {
        val enablePin = propertyManager.getBoolean(KEY_PIN, false)

        if (enablePin) {
            getView()?.showPinDialog()
        }
    }

    override fun clickWriteButton() {
        getView()?.navigateToWrite()
    }

    override fun clickAdCancel() {
        getView()?.dismissAdView()
    }

    override fun clickAdClose() {
        (getView() as? Activity)?.finish()
    }

    override fun initAd() {
        getView()?.initAdView(AdRequest.Builder().build())
    }

    override fun onBackPress() {
        if (backPressCloseHandler.isCloseable()) {
            getView()?.showAdView()
        } else {
            getView()?.showToast("\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.")
        }
    }
}