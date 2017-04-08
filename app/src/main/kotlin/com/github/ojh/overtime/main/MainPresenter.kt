package com.github.ojh.overtime.main

import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainPresenter<V : MainContract.View> @Inject constructor()
    : BasePresenter<V>(), MainContract.Presenter<V> {

    override fun clickWriteButton() {
        getView()?.navigateToWrite()
    }

}