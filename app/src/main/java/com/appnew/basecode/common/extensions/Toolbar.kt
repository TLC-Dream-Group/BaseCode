package com.appnew.basecode.common.extensions

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Typeface
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.toColorInt

fun Toolbar.setGradientTitle(
    context: Context,
    title: String,
    startColor: String = "#2AACFF",
    endColor: String = "#006FFF",
    textSizeSp: Float = 22f
) {
    val textView = TextView(context).apply {
        text = title.uppercase()
        textSize = textSizeSp
        typeface = Typeface.DEFAULT_BOLD

        val paint = paint
        val width = paint.measureText(title)
        val textShader = LinearGradient(
            0f, 0f, width, 0f,
            intArrayOf(startColor.toColorInt(), endColor.toColorInt()),
            null,
            Shader.TileMode.MIRROR
        )
        paint.shader = textShader
    }

    this.title = ""
    this.addView(textView)
}