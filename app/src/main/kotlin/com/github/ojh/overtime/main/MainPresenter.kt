package com.github.ojh.overtime.main

import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.util.PropertyUtil
import javax.inject.Inject

class MainPresenter<V : MainContract.View> @Inject constructor(
        private val propertyUtil: PropertyUtil

) : BasePresenter<V>(), MainContract.Presenter<V> {

    companion object {
        const val KEY_PIN = "key_pin"
    }

    override fun getPinSettings() {
        val enablePin = propertyUtil.getBoolean(KEY_PIN, false)

        if(enablePin) {
            getView()?.showPinDialog()
        }
    }

    override fun clickWriteButton() {
        getView()?.navigateToWrite()
    }

}