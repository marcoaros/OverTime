package com.github.ojh.overtime.detail

import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailPresenter<V : DetailContract.View> @Inject constructor(
        private val dataManager: DataManager
) : BasePresenter<V>(), DetailContract.Presenter<V> {

    override fun init(timeLineId: Int) {
        addDisposable(
                dataManager.getTimeLine(timeLineId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            getView()?.initView(it)
                        }
        )
    }
}