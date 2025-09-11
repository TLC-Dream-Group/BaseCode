@file:Suppress("UNUSED")

package com.appnew.basecode.common.extensions

import android.animation.Animator
import android.view.ViewPropertyAnimator

fun ViewPropertyAnimator.setListener(
    onAnimationStart: ((Animator) -> Unit)? = null,
    onAnimationCancel: ((Animator) -> Unit)? = null,
    onAnimationRepeat: ((Animator) -> Unit)? = null,
    onAnimationEnd: ((Animator) -> Unit)? = null,
    ):ViewPropertyAnimator{
    setListener(object : Animator.AnimatorListener{
        override fun onAnimationStart(animation: Animator) {
            onAnimationStart?.invoke(animation)
        }

        override fun onAnimationEnd(animation: Animator) {
            onAnimationEnd?.invoke(animation)
        }

        override fun onAnimationCancel(animation: Animator) {
            onAnimationCancel?.invoke(animation)
        }

        override fun onAnimationRepeat(animation: Animator) {
            onAnimationRepeat?.invoke(animation)
        }
    })
    return this
}