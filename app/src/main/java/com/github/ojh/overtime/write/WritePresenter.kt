package com.github.ojh.overtime.write

import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import com.github.ojh.overtime.data.model.Events
import com.github.ojh.overtime.data.model.TimeLine
import com.github.ojh.overtime.util.EventBus
import com.github.ojh.overtime.util.RealmUtil
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
        getView()?.initView()
    }

    override fun clickSave(timeLine: TimeLine) {
        if (timeLine.isValidAll()) {
            dataManager.saveTimeLine(timeLine)
            getView()?.navigateToMain()
            EventBus.post(Events.WriteEvent())
        } else {
            getView()?.setErrorContent(true)
        }
    }

    override fun validateContent(content: String?) {
        val isError = content == null || content.isEmpty()
        getView()?.setErrorContent(isError)
    }
}