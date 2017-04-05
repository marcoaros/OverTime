package com.github.ojh.overtime.util.extensions

import android.annotation.TargetApi
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.transition.Transition
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.ImageViewTarget
import com.github.ojh.overtime.util.PaletteColorCallback
import com.github.ojh.overtime.util.palette.PaletteBitmap
import com.github.ojh.overtime.util.palette.PaletteBitmapTranscoder
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.load(uri: String) {
    Glide.with(context)
            .load(uri)
            .centerCrop()
            .into(this)
}

fun ImageView.loadFromPalette(url: String, paletteColorCallback: PaletteColorCallback) {
    Glide.with(context)
            .fromString()
            .asBitmap()
            .transcode(PaletteBitmapTranscoder(context), PaletteBitmap::class.java)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .load(url)
            .into(object : ImageViewTarget<PaletteBitmap>(this) {

                override fun setResource(resource: PaletteBitmap?) {
                    super.view.setImageBitmap(resource?.bitmap)

                    val swatch = resource?.palette?.run {
                        vibrantSwatch
                                ?: darkVibrantSwatch
                                ?: lightVibrantSwatch
                                ?: mutedSwatch
                                ?: darkMutedSwatch
                                ?: lightMutedSwatch
                    }

                    swatch?.let {
                        paletteColorCallback.invoke(it.rgb, it.titleTextColor, it.bodyTextColor)
                    }

                }
            })
}

fun Date.toFormatString(format: String = "MM/dd"): String {
    val formatter = SimpleDateFormat(format, Locale.KOREA)
    return formatter.format(this)
}

fun Date.toWeekString(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return when (calendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.SUNDAY -> "일"
        Calendar.MONDAY -> "월"
        Calendar.TUESDAY -> "화"
        Calendar.WEDNESDAY -> "수"
        Calendar.THURSDAY -> "목"
        Calendar.FRIDAY -> "금"
        Calendar.SATURDAY -> "토"
        else -> throw IllegalArgumentException("is not valid week")
    }
}

fun Date.getBetweenDates(): Pair<Date, Date> {

    val curCal = Calendar.getInstance()
    curCal.time = this

    val year = curCal.get(Calendar.YEAR)
    val month = curCal.get(Calendar.MONTH)
    val day = curCal.get(Calendar.DAY_OF_MONTH)

    val startCal = Calendar.getInstance()
    startCal.set(year, month, day, 0, 0, 0)

    val endCal = Calendar.getInstance()
    endCal.set(year, month, day, 0, 0, 0)
    endCal.add(Calendar.DAY_OF_MONTH, 1)
    endCal.add(Calendar.SECOND, -1)

    val startDate = startCal.time
    val endDate = endCal.time

    return Pair(startDate, endDate)
}

fun EditText.setOnSimpleTextWather(listener: (text: String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            listener.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun Transition.addSimpleEndTransitionListener(action: () -> Unit) {
    this.addListener(object : Transition.TransitionListener {
        override fun onTransitionEnd(transition: Transition) {
            removeListener(this)
            action.invoke()
        }

        override fun onTransitionCancel(transition: Transition) {
            removeListener(this)
            action.invoke()
        }

        override fun onTransitionPause(transition: Transition) {
            removeListener(this)
            action.invoke()
        }

        override fun onTransitionResume(transition: Transition) {}
        override fun onTransitionStart(transition: Transition) {}
    })
}