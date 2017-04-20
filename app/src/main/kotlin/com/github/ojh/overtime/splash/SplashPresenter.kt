package com.github.ojh.overtime.splash

import android.app.Application
import android.os.Handler
import com.airbnb.lottie.LottieAnimationView
import com.github.ojh.overtime.alarm.AlarmUtil
import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.util.PropertyManager
import com.github.ojh.overtime.util.PropertyManager.Companion.KEY_ALARM
import com.github.ojh.overtime.util.firebase.FirebaseUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashPresenter<V : SplashContract.View> @Inject constructor(
        private val application: Application,
        private val propertyManager: PropertyManager

) : BasePresenter<V>(), SplashContract.Presenter<V> {

    override fun initFirebaseRemoteConfig() {
        FirebaseUtil.fetch()
    }

    override fun initAlarm() {
        addDisposable(
                propertyManager.getBoolean(KEY_ALARM, true)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            AlarmUtil.setOnceAlarm(application, 22, 0)
                        }
        )

    }

    override fun initLottieView(views: Array<LottieAnimationView>) {
        addDisposable(
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