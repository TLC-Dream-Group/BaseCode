package com.appnew.basecode.common.extensions

import android.content.Context
import androidx.fragment.app.Fragment

@Suppress("Deprecation")
inline fun <reified T : Any> Fragment.extraNotNull(key: String, default: T? = null) = lazy {
    val value = arguments?.get(key)
    requireNotNull(value as? T ?: default) { key }
}


inline fun Fragment.withSafeContext(action: (Context) -> Unit) {
    if (!isAdded || context == null) return
    val ctx = requireContext()
    action(ctx)
}