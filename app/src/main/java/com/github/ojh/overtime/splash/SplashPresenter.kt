package com.github.ojh.overtime.splash

import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by ohjaehwan on 2017. 2. 27..
 */
class SplashPresenter<V : SplashContract.View> @Inject constructor(
        dataManager: DataManager, compositeDisposable: CompositeDisposable
) : BasePresenter<V>(dataManager, compositeDisposable),
        SplashContract.Presenter<V> {

    override fun init() {
        compositeDisposable.add(
                Observable.timer(2, TimeUnit.SECONDS).subscribe {
                    getView()?.navigateToTimeLine()
                }
        )
    }

}