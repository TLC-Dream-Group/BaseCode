@file:Suppress("UNUSED")

package com.appnew.basecode.common.extensions

import android.content.res.Resources
import kotlin.math.roundToInt

fun Int.dp(): Int {
    val density = Resources.getSystem().displayMetrics.density
    return (this * density).roundToInt()
}
fun Int.dpFloat(): Float {
    val density = Resources.getSystem().displayMetrics.density
    return (this * density)
}