package com.github.ojh.overtime.main

import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataSource
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by OhJaeHwan on 2017-02-28.
 */
class MainPresenter<V : MainContract.View> @Inject constructor(
        dataSource: DataSource, compositeDisposable: CompositeDisposable
) : BasePresenter<V>(dataSource, compositeDisposable), MainContract.Presenter<V> {

    override fun clickFabWrite() {
        getView().showToast("토스트 먹고싶다.")
    }
}