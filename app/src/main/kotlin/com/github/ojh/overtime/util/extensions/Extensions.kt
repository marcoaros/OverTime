package com.github.ojh.overtime.util.extensions

import android.annotation.TargetApi
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.transition.Transition
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.load(uri: Uri) {
    Glide.with(context)
            .load(uri)
            .centerCrop()
            .into(this)
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