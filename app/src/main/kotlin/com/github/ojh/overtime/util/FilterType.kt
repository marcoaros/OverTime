package com.github.ojh.overtime.util

fun Int.toFilterType() = when (this) {
    0 -> FilterDateDescending()
    1 -> FilterDateAscending()
    else -> throw IllegalStateException("$this is not filter type")
}

sealed class FilterType

class FilterDateDescending : FilterType()
class FilterDateAscending : FilterType()




