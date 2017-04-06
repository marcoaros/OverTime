package com.github.ojh.overtime.write

import org.junit.Test
import java.util.*

class TestKotlin {

    @Test
    fun calendarTest() {


        val curCal = Calendar.getInstance()

        val year = curCal.get(Calendar.YEAR)
        val month = curCal.get(Calendar.MONTH)
        val day = curCal.get(Calendar.DAY_OF_MONTH)

        val startCal = Calendar.getInstance()
        startCal.set(year, month, day, 0, 0, 0)

        val endCal = Calendar.getInstance()
        endCal.set(year, month, day, 0, 0, 0)
        endCal.add(Calendar.DAY_OF_MONTH, 1)
        endCal.add(Calendar.SECOND, -1)

        println(startCal)
        println(endCal)
    }


    @Test
    fun testFlatMap() {
        val list = listOf(listOf("1", "2"), listOf(3), listOf("4" to "44", "5" to "55", "6" to "66"))

        list.flatMap {
            it
        }.forEach(::println)

    }

}