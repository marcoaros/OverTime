package com.github.ojh.overtime.util

import android.view.View

typealias ViewClickHandler = (view: View, position: Int) -> Unit
typealias SimpleCallback = () -> Unit
typealias PaletteColorCallback = (rgb: Int, titleColor: Int ,bodyColor: Int) -> Unit