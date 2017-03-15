package com.github.ojh.overtime.util

import com.github.ojh.overtime.data.model.Events
import io.reactivex.subjects.PublishSubject

object EventBus {
    val bus: PublishSubject<Events> = PublishSubject.create()

    fun post(event: Events) {
        bus.onNext(event)
    }
}