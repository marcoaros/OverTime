package com.github.ojh.overtime.util

import android.text.Editable
import android.text.TextWatcher

abstract class SimpleTextWatcher : TextWatcher {
    abstract override fun afterTextChanged(s: Editable?)

    final override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    final override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}