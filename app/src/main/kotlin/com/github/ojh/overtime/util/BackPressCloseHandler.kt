package com.github.ojh.overtime.util

class BackPressCloseHandler() {

    private var backKeyPressedTime: Long = 0

    fun isCloseable(): Boolean =
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis()
                false

            } else {
                true
            }
}