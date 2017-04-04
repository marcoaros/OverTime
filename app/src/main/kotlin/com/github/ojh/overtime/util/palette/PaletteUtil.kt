package com.github.ojh.overtime.util.palette

import android.support.v7.graphics.Palette

object PaletteUtil {
    fun getSwatch(palette: Palette?): Palette.Swatch? {

        return palette?.lightVibrantSwatch
                ?: palette?.lightMutedSwatch
                ?: palette?.vibrantSwatch
                ?: palette?.mutedSwatch
                ?: palette?.darkVibrantSwatch
                ?: palette?.darkMutedSwatch
    }
}