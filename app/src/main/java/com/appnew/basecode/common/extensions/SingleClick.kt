@file:Suppress("UNUSED")

package com.appnew.basecode.common.extensions

import android.os.SystemClock
import android.view.View

abstract class OnSingleClickListener(private val minClickInterval: Long = MIN_CLICK_INTERVAL) :
    View.OnClickListener {

    private var mLastClickTime: Long = 0

    abstract fun onSingleClick(v: View?)

    override fun onClick(v: View?) {
        val currentClickTime: Long = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - mLastClickTime
        mLastClickTime = currentClickTime
        if (elapsedTime <= minClickInterval) return
        onSingleClick(v)
    }

    companion object {
        const val MIN_CLICK_INTERVAL: Long = 300
    }
}

fun View.click(
    minClickInterval: Long,
    listener: (View) -> Unit
) {
    this.setOnClickListener(object : OnSingleClickListener(minClickInterval) {
        override fun onSingleClick(v: View?) {
            listener.invoke(v ?: this@click)
        }
    })
}

fun View.click(
    listener: (View) -> Unit
) {
    this.setOnClickListener(object : OnSingleClickListener() {
        override fun onSingleClick(v: View?) {
            listener.invoke(v ?: this@click)
        }
    })
}

private var isClickEnabled = true

fun View.setSingleClickListener(debounceTime: Long = 500L, action: (View) -> Unit) {
    setOnClickListener {
        if (isClickEnabled) {
            isClickEnabled = false
            action(it)
            // Re-enable clicks after debounce time
            postDelayed({ isClickEnabled = true }, debounceTime)
        }
    }
}