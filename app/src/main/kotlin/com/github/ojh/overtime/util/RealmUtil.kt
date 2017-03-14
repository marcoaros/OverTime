package com.github.ojh.overtime.util

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposables
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmObject

object RealmUtil {

    fun save(realmObject: RealmObject) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(realmObject)
        realm.commitTransaction()
        realm.close()
    }

    fun delete(realmObject: RealmObject) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realmObject.deleteFromRealm()
        realm.commitTransaction()
        realm.close()
    }

    inline fun <reified T : RealmObject> getNextId(): Int {
        val realm = Realm.getDefaultInstance()
        val nextId = (realm.where(T::class.java).max("id")?.toInt() ?: 0) + 1
        realm.close()
        return nextId
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