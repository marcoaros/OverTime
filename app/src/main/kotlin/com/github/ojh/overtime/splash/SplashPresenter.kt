package com.github.ojh.overtime.splash

import android.app.Application
import android.os.Handler
import com.airbnb.lottie.LottieAnimationView
import com.github.ojh.overtime.alarm.AlarmUtil
import com.github.ojh.overtime.base.BasePresenter
import com.github.ojh.overtime.util.PropertyUtil
import com.github.ojh.overtime.util.PropertyUtil.Companion.KEY_ALARM
import com.github.ojh.overtime.util.firebase.FirebaseUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashPresenter<V : SplashContract.View> @Inject constructor(
        private val application: Application,
        private val propertyUtil: PropertyUtil

) : BasePresenter<V>(), SplashContract.Presenter<V> {

    override fun init(views: Array<LottieAnimationView>) {

        FirebaseUtil.fetch()
        initAlarm()

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

    private fun initAlarm() {
        if (propertyUtil.getBoolean(KEY_ALARM, true)) {
            AlarmUtil.setOnceAlarm(application, 22, 0)
        }
    }

}