package com.github.ojh.overtime.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.support.annotation.ColorRes
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import com.github.ojh.overtime.R

object GUIUtils {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun animateRevealHide(ctx: Context, view: View, @ColorRes color: Int,
                          finalRadius: Int, animEndListener: () -> Unit) {

        val cx = (view.left + view.right) / 2
        val cy = (view.top + view.bottom) / 2
        val initialRadius = view.width

        val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy,
                initialRadius.toFloat(), finalRadius.toFloat())

        anim.apply {
            addListener(
                    object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator) {
                            super.onAnimationStart(animation)
                            view.setBackgroundColor(ctx.resources.getColor(color))
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
    fun animateRevealShow(ctx: Context, view: View, startRadius: Int,
                          @ColorRes color: Int, x: Int, y: Int, animEndListener: () -> Unit) {
        val finalRadius = Math.hypot(view.width.toDouble(), view.height.toDouble()).toFloat()

        val anim = ViewAnimationUtils.createCircularReveal(view, x, y, startRadius.toFloat(), finalRadius)
        anim.duration = ctx.resources.getInteger(R.integer.animation_duration).toLong()
        anim.startDelay = 100
        anim.interpolator = AccelerateDecelerateInterpolator()
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                view.setBackgroundColor(ctx.resources.getColor(color))
            }

            override fun onAnimationEnd(animation: Animator) {
                view.visibility = View.VISIBLE
                animEndListener.invoke()
            }
        })
        anim.start()
    }
}
