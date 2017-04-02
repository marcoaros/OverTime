package com.github.ojh.overtime.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import com.github.ojh.overtime.R

object GUIUtils {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun animateRevealHide(view: View, animEndListener: () -> Unit) {
        val ctx = view.context
        val cx = (view.left + view.right) / 2
        val cy = (view.top + view.bottom) / 2
        val finalRadius = 0f
        val initialRadius = view.width

        val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy,
                initialRadius.toFloat(), finalRadius)

        anim.apply {
            addListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator) {
                            super.onAnimationStart(animation)

                            val typedValue = TypedValue()
                            ctx.theme.resolveAttribute(R.attr.colorAccent, typedValue, true)

                            view.setBackgroundColor(typedValue.data)
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            animEndListener.invoke()
                            view.visibility = View.INVISIBLE
                        }
                    })

            anim.duration = ctx.resources.getInteger(R.integer.animation_duration).toLong()
        }.start()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun animateRevealShow(view: View, animEndListener: () -> Unit) {
        val ctx = view.context
        val x = (view.left + view.right) / 2
        val y = (view.top + view.bottom) / 2
        val startRadius = 0f

        val finalRadius = Math.hypot(view.width.toDouble(), view.height.toDouble()).toFloat()

        val anim = ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, finalRadius).apply {
            duration = ctx.resources.getInteger(R.integer.animation_duration).toLong()
            startDelay = 100
            interpolator = AccelerateDecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {

                    val typedValue = TypedValue()
                    ctx.theme.resolveAttribute(R.attr.colorAccent, typedValue, true)

                    view.setBackgroundColor(typedValue.data)
                }

                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.VISIBLE
                    animEndListener.invoke()
                }
            })
        }
        anim.start()
    }
}
