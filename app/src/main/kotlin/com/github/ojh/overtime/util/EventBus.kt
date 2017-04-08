package com.github.ojh.overtime.util

import com.github.ojh.overtime.data.Events
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

object EventBus {
    private val bus: Relay<Events> = PublishRelay.create<Events>().toSerialized()

    fun post(event: Events) {
        bus.accept(event)
    }

    fun asFlowable(): Flowable<Events> {
        return bus.toFlowable(BackpressureStrategy.LATEST)
    }
}