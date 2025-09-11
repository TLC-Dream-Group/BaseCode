@file:Suppress("unused")

package com.appnew.basecode.utils

//noinspection SuspiciousImport
import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.StyleRes
import androidx.core.content.res.use
import kotlin.math.max
import kotlin.math.min

object ColorUtils {

    @Suppress("unused")
    fun createCheckboxTintList(checkedColor: Int, uncheckedColor: Int): ColorStateList {
        return ColorStateList(
            arrayOf(
                intArrayOf(R.attr.state_checked),  // Checked state
                intArrayOf(-R.attr.state_checked)  // Unchecked state
            ),
            intArrayOf(
                checkedColor,   // Blue tint when checked
                uncheckedColor  // Black tint when unchecked
            )
        )
    }


    @ColorInt
    fun withAlpha(@ColorInt baseColor: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float): Int {
        val a = min(255, max(0, (alpha * 255).toInt())) shl 24
        val rgb = 0x00ffffff and baseColor
        return a + rgb
    }

    fun @receiver:ColorInt Int.addAlpha(alpha: Float): Int {
        return withAlpha(this, alpha)
    }

    val Int.colorStateList
        get() = ColorStateList.valueOf(this)

    @JvmOverloads
    fun resolveColor(context: Context, @AttrRes attr: Int, fallback: Int = 0): Int {
        context.theme.obtainStyledAttributes(intArrayOf(attr)).use {
            return try {
                it.getColor(0, fallback)
            } catch (e: Exception) {
                fallback
            }
        }
    }

    fun resolveColor(
        context: Context,
        @StyleRes resId: Int,
        @AttrRes attr: Int,
        fallback: Int = 0
    ): Int {
        context.theme.obtainStyledAttributes(resId, intArrayOf(attr)).use {
            return try {
                it.getColor(0, fallback)
            } catch (e: Exception) {
                fallback
            }
        }
    }

    fun colorIntToHex(@ColorInt color: Int): String {
        // Convert color int to hex, removing alpha channel if present
        return String.format("#%06X", (0xFFFFFF and color))
    }

    //true white
    fun isWhite(contentColor: String?): Boolean {
        if (contentColor.isNullOrBlank()) return false

        val normalized = contentColor.trim().replace("#", "").uppercase()

        return normalized.contains("FFFFFF") || normalized.contains("FFF")
    }

    fun isColorLight(@ColorInt color: Int): Boolean {
        val darkness =
            1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness < 0.4
    }

    @ColorInt
    fun darkenColor(@ColorInt color: Int): Int {
        return shiftColor(color, 0.9f)
    }

    @ColorInt
    fun shiftColor(@ColorInt color: Int, @FloatRange(from = 0.0, to = 2.0) by: Float): Int {
        if (by == 1f) return color
        val alpha = Color.alpha(color)
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] *= by // value component
        return (alpha shl 24) + (0x00ffffff and Color.HSVToColor(hsv))
    }

}