package com.github.ojh.overtime.write

import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by ohjaehwan on 2017. 3. 6..
 */
class WritePresenter<V : WriteContract.View> @Inject constructor(
        dataManager: DataManager,
        compositeDisposable: CompositeDisposable
) : BasePresenter<V>(dataManager, compositeDisposable), WriteContract.Presenter<V> {

    override fun init() {
        getView().initView()
    }

    override fun clickSave() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}