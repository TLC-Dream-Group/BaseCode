@file:Suppress("UNUSED")

package com.appnew.basecode.common.extensions

import android.app.Activity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.appnew.basecode.utils.VersionUtils
import com.appnew.basecode.utils.ColorUtils

@Suppress("Deprecation")
fun AppCompatActivity.setStatusBarColor(@ColorInt color: Int) {
    if (VersionUtils.hasOreo()) {
        window.statusBarColor = color
    } else {
        window.statusBarColor = ColorUtils.darkenColor(color)
    }
}

fun AppCompatActivity.hideStatusBar() {
    WindowInsetsControllerCompat(window, window.decorView).apply {
        systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        hide(WindowInsetsCompat.Type.navigationBars())
    }
}
/**
 * Sets the status bar appearance to light or dark based on the `isLight` flag.
 *
 * @param isLight If true, the status bar text and icons will be set to dark (for light backgrounds).
 *                If false, the status bar text and icons will be set to light (for dark backgrounds).
 *
 * Note: This function must be called after `setContentView()` in the Activity lifecycle,
 *       as it modifies the window's system UI appearance, which is tied to the view hierarchy.
 */
fun AppCompatActivity.setLightStatusBar(isLight: Boolean) {
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightStatusBars = isLight
    }
}

/**
 * Sets the navigation bar appearance to light or dark based on the `isLight` flag.
 *
 * @param isLight If true, the status bar text and icons will be set to dark (for light backgrounds).
 *                If false, the status bar text and icons will be set to light (for dark backgrounds).
 *
 * Note: This function must be called after `setContentView()` in the Activity lifecycle,
 *       as it modifies the window's system UI appearance, which is tied to the view hierarchy.
 */
fun AppCompatActivity.setLightNavigationBar(isLight: Boolean) {
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightNavigationBars = isLight
    }
}


/**
 * Sets the system bars appearance to light or dark based on the `isLight` flag.
 *
 * @param isLight If true, the status bar text and icons will be set to dark (for light backgrounds).
 *                If false, the status bar text and icons will be set to light (for dark backgrounds).
 *
 * Note: This function must be called after `setContentView()` in the Activity lifecycle,
 *       as it modifies the window's system UI appearance, which is tied to the view hierarchy.
 */
fun AppCompatActivity.setLightSystemBars(isLight: Boolean) {
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightStatusBars = isLight
        isAppearanceLightNavigationBars = isLight
    }
}


fun Activity.hideSoftKeyboard(view: View? = null) {
    val currentFocus: View? = view ?: currentFocus
    if (currentFocus != null) {
        val inputMethodManager =
            getSystemService<InputMethodManager>()
        inputMethodManager?.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}

fun Activity.showSoftKeyboard(view: View? = null) {
    val currentFocus: View? = view ?: currentFocus
    if (currentFocus != null) {
        val inputMethodManager =
            getSystemService<InputMethodManager>()
        inputMethodManager?.showSoftInput(currentFocus, 0)
    }
}





