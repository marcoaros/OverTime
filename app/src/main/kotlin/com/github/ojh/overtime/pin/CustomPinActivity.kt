package com.github.ojh.overtime.pin

import com.github.ojh.overtime.main.MainPresenter.Companion.KEY_PIN
import com.github.ojh.overtime.util.PropertyManager
import com.github.ojh.overtime.util.extensions.toast
import com.github.orangegangsters.lollipin.lib.managers.AppLockActivity


class CustomPinActivity : AppLockActivity() {

    override fun showForgotDialog() {
        val propertyUtil = PropertyManager(this)
        propertyUtil.setBoolean(KEY_PIN, false)
        toast("비밀번호 초기화")
        finish()
    }

    override fun onPinFailure(attempts: Int) {

    }

    override fun onPinSuccess(attempts: Int) {

    }

    override fun getPinLength(): Int {
        return super.getPinLength()//you can override this method to change the pin length from the default 4
    }
}
