package com.github.ojh.overtime.data

import com.github.ojh.overtime.data.model.TimeLine
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposables
import io.realm.Realm
import io.realm.RealmChangeListener

/**
 * Created by OhJaeHwan on 2017-02-28.
 */
class LocalDataSource : DataSource {

    override fun getTimeLines(): Flowable<List<TimeLine>> {
        val realm = Realm.getDefaultInstance()
        val results = realm.where(TimeLine::class.java).findAll()
        val list: List<TimeLine> = results.toList()
        return Flowable.just(list)
//        getRealm().map { it.where(TimeLine::class.java).findAll() }
    }

    private fun getRealm(): Flowable<Realm> {
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


    private fun getObservableRealm(): Observable<Realm> {
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