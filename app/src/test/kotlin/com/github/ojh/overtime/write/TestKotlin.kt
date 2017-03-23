package com.github.ojh.overtime.write

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import java.util.concurrent.TimeUnit

class TestKotlin {

    @Test
    fun test() {

        val startTime = System.currentTimeMillis()

        val views = listOf(1, 2, 3, 4, 5)


        Observable.intervalRange(0, views.size.toLong(), 0, 300, TimeUnit.MILLISECONDS)
                .map(Long::toInt)
                .delay(300, TimeUnit.MICROSECONDS, Schedulers.trampoline())
                .doOnComplete {
                    println("complete time = ${System.currentTimeMillis() - startTime}")
                }
                .doOnEach {
                    println("doOnEach $it.value = ${System.currentTimeMillis() - startTime}")
                }
                .subscribe {
                    println("subsribe - ${System.currentTimeMillis() - startTime}")
                }



        Thread.sleep(5000)
    }
}