package com.github.ojh.overtime.util

import android.os.Handler
import android.os.Looper
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposables
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmObject

/**
 * Created by ohjaehwan on 2017. 3. 7..
 */
object RealmUtil {

    fun execute(action: () -> RealmObject) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val realmData = action.invoke()
        realm.copyToRealmOrUpdate(realmData)
        realm.commitTransaction()
        realm.close()
    }

    fun getRealm(): Flowable<Realm> {
        return Flowable.create({ emitter ->
            val observableRealm = Realm.getDefaultInstance()

            val listener = RealmChangeListener<Realm> { _realm ->
                emitter.onNext(_realm)
            }

            emitter.setDisposable(Disposables.fromRunnable {
                observableRealm.removeChangeListener(listener)
                observableRealm.close()
            })

            observableRealm.addChangeListener(listener)
            emitter.onNext(observableRealm)
        }, BackpressureStrategy.LATEST)

    }


    fun getObservableRealm(): Observable<Realm> {
        return Observable.create {
            ObservableOnSubscribe<Realm> { emitter ->
                val observableRealm = Realm.getDefaultInstance()

                val listener = RealmChangeListener<Realm> { _realm ->
                    if (!emitter.isDisposed) {
                        emitter.onNext(_realm)
                    }
                }

                emitter.setDisposable(Disposables.fromRunnable {
                    observableRealm.removeChangeListener(listener)
                    observableRealm.close()
                })

                observableRealm.addChangeListener(listener)
                emitter.onNext(observableRealm)
            }

        }
    }
}