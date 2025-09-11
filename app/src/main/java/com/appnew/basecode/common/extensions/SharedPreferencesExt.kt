@file:Suppress("UNUSED")

package com.appnew.basecode.common.extensions

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

@Suppress("UNCHECKED_CAST", "ALL")
inline fun <reified T> SharedPreferences.put(key: String, value: T) {
    when (value) {
        is String -> edit().putString(key, value).apply()
        is Int -> edit().putInt(key, value).apply()
        is Boolean -> edit().putBoolean(key, value).apply()
        is Float -> edit().putFloat(key, value).apply()
        is Long -> edit().putLong(key, value).apply()
        is Set<*> -> edit { putStringSet(key, value as Set<String>) }
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}

inline fun <reified T> SharedPreferences.get(key: String, defaultValue: T): T {
    return when (T::class) {
        String::class -> getString(key, defaultValue as String) as T
        Int::class -> getInt(key, defaultValue as Int) as T
        Boolean::class -> getBoolean(key, defaultValue as Boolean) as T
        Float::class -> getFloat(key, defaultValue as Float) as T
        Long::class -> getLong(key, defaultValue as Long) as T
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}

fun SharedPreferences.remove(key: String) {
    edit { remove(key) }
}

@SharedPrefPrimitiveOnly
@Suppress("unused")
inline fun <reified T> SharedPreferences.getAsFlow(
    keyForValue: String,
    defaultValue: T
) = callbackFlow {
    val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (keyForValue == key) {
            trySend(get(key, defaultValue))
        }
    }
    registerOnSharedPreferenceChangeListener(listener)
    send(
        get(
            keyForValue,
            defaultValue
        )
    ) // if you want to emit an initial default value
    awaitClose { unregisterOnSharedPreferenceChangeListener(listener) }
}


@RequiresOptIn(
    level = RequiresOptIn.Level.WARNING,
    message = "Only primitive types (String, Int, Boolean, Float, Long) are supported in SharedPreferences.get() / SharedPreferences.put()"
)
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class SharedPrefPrimitiveOnly