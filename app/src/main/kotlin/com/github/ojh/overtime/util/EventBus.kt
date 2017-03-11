package com.github.ojh.overtime.util

import io.reactivex.subjects.PublishSubject
import java.util.*

object EventBus {
    val bus: PublishSubject<Any> = PublishSubject.create()

    fun post(event: Any) {
        bus.onNext(event)
    }
}