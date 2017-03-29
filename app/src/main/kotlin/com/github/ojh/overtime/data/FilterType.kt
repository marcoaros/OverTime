package com.github.ojh.overtime.data

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

class FilterDateDescending : FilterType()
class FilterDateAscending : FilterType()




