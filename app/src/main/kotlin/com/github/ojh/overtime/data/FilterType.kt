package com.github.ojh.overtime.data

import java.util.*

fun Int.toFilterType() = when (this) {
    0 -> FilterDateDescending()
    1 -> FilterDateAscending()
    else -> throw IllegalStateException("$this is not filter type")
}

sealed class FilterType {
    fun toPosition(): Int = when(this) {
        is FilterDateDescending -> 0
        is FilterDateAscending -> 1
    }
}

open class FilterDateDescending : FilterType()

open class FilterDateAscending : FilterType()

class FilterEqualDate(val date: Date): FilterDateDescending()




