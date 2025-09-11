@file:Suppress("UNUSED")

package com.appnew.basecode.common.extensions

import android.graphics.Rect
import android.view.View
import androidx.annotation.FloatRange
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun View.visible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

inline fun View.visibleIf(block: () -> Boolean) {
    visibility = if (block()) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

inline fun View.visibleGoneIf(block: () -> Boolean) {
    visibility = if (block()) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.invisible() {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
}

inline fun View.invisibleIf(block: () -> Boolean) {
//    if (visibility != View.INVISIBLE && block()) {
    if (block()) {
        visibility = View.INVISIBLE
    }
}

fun View.gone() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

inline fun View.goneIf(block: () -> Boolean) {
    if (visibility != View.GONE && block()) {
        visibility = View.GONE
    }
}

fun View.bounceScale(@FloatRange(from = 0.1, to = 1.0) shrinkFactor: Float = 1f) {
    val startVelocity = -4f
    val springForce = SpringForce(1f).apply {
        dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        stiffness = SpringForce.STIFFNESS_LOW
    }

    listOf(DynamicAnimation.SCALE_X, DynamicAnimation.SCALE_Y).forEach { property ->
        SpringAnimation(this, property).apply {
            spring = springForce
            setStartValue(shrinkFactor)
            setStartVelocity(startVelocity)
            start()
        }
    }
}



fun View.applyKeyboardInsets() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
        val bottomInset = maxOf(systemBars.bottom, imeInsets.bottom)

        v.setPadding(systemBars.left, systemBars.top, systemBars.right, bottomInset)
        insets
    }
}

fun View.applySystemBarsInsets() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
    }
}

fun View.applyKeyboardInsetsWithFallback(onKeyboardHeightChanged: (Int) -> Unit = {}) {
//    var insetsFired = false

    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        val bottomInset = maxOf(systemBars.bottom, imeHeight)
//        if (bottomInset > 0) {
//            insetsFired = true
//        }
        onKeyboardHeightChanged(bottomInset)
        insets
    }

    // Fallback: only works if insets never fired
//    viewTreeObserver.addOnGlobalLayoutListener {
//        if (!insetsFired) {
//            val rect = Rect()
//            getWindowVisibleDisplayFrame(rect)
//            val screenHeight = rootView.height
//            val keyboardHeight = screenHeight - rect.bottom
//            onKeyboardHeightChanged(keyboardHeight)
//        }
//    }
}

fun RecyclerView.addBottomMarginToLastItem(marginPx: Int) {
    this.addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val layoutManager = parent.layoutManager
            val adapter = parent.adapter ?: return
            val position = parent.getChildAdapterPosition(view)
            val itemCount = adapter.itemCount

            when (layoutManager) {
                is GridLayoutManager -> {
                    val spanCount = layoutManager.spanCount
                    val rowCount = (itemCount + spanCount - 1) / spanCount
                    val itemRow = (position / spanCount) + 1
                    if (itemRow == rowCount) {
                        outRect.bottom = marginPx
                    }
                }

                is LinearLayoutManager -> {
                    if (position == itemCount - 1) {
                        outRect.bottom = marginPx
                    }
                }

                else -> {
                    // default fallback
                    if (position == itemCount - 1) {
                        outRect.bottom = marginPx
                    }
                }
            }
        }
    })
}
