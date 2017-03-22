package com.github.ojh.overtime.splash

import android.os.Handler
import com.airbnb.lottie.LottieAnimationView
import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.data.DataManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashPresenter<V : SplashContract.View> @Inject constructor(
        dataManager: DataManager,
        compositeDisposable: CompositeDisposable

) : BasePresenter<V>(dataManager, compositeDisposable), SplashContract.Presenter<V> {

    override fun init(views: Array<LottieAnimationView>) {

        compositeDisposable.add(
                Observable.intervalRange(0, views.size.toLong(), 0, 300, TimeUnit.MILLISECONDS)
                        .map(Long::toInt)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete {
                            Handler().postDelayed({
                                getView()?.navigateToTimeLine()
                            }, 1000)
                        }
                        .subscribe {
                            views[it].playAnimation()
                        }
        )
    }

}