package com.github.ojh.overtime.splash

import android.os.Handler
import android.os.Looper
import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SplashPresenter<V : SplashContract.View> @Inject constructor(
        dataManager: DataManager,
        compositeDisposable: CompositeDisposable

) : BasePresenter<V>(dataManager, compositeDisposable), SplashContract.Presenter<V> {

    override fun init() {
        Handler(Looper.getMainLooper()).postDelayed({
            getView()?.navigateToTimeLine()
        }, 2000)

//        compositeDisposable.add(
//                Observable.timer(2, TimeUnit.SECONDS).subscribe {
//                    getView()?.navigateToTimeLine()
//                }
//        )
    }

}