@file:Suppress("UNUSED")

package com.appnew.basecode.common.extensions

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import com.appnew.basecode.utils.VersionUtils
import java.io.Serializable

@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Intent.getParcelableExtraProvider(identifierParameter: String): T? {
    return try {
        if (VersionUtils.hasT()) {
            this.getParcelableExtra(identifierParameter, T::class.java)
        } else {
            this.getParcelableExtra(identifierParameter)
        }
    } catch (e: Exception) {
        null
    }
}

inline fun <reified T : Parcelable> Intent.getParcelableExtraProviderWithDefault(
    identifierParameter: String,
    defaultValue: T,
): T {
    return getParcelableExtraProvider(identifierParameter) ?: defaultValue
}

@Suppress("DEPRECATION")
inline fun <reified T : Serializable> Intent.getSerializableExtraProvider(identifierParameter: String): T? {
    return if (VersionUtils.hasT()) {
        this.getSerializableExtra(identifierParameter, T::class.java)
    } else {
        this.getSerializableExtra(identifierParameter) as T?
    }
}

inline fun <reified T : Serializable> Intent.getSerializableExtraProvider(
    identifierParameter: String,
    defaultValue: T,
): T {
    return getSerializableExtraProvider(identifierParameter) ?: defaultValue
}

@Suppress("UNUSED", "DEPRECATION")
inline fun <reified T : Parcelable> Intent.getParcelablesExtraProvider(
    identifierParameter: String
): ArrayList<T> {
    return if (VersionUtils.hasT()) {
        this.getParcelableArrayListExtra(identifierParameter, T::class.java)
    } else {
        this.getParcelableArrayListExtra(identifierParameter)
    } ?: arrayListOf()
}

@Suppress("UNUSED", "DEPRECATION")
inline fun <reified T : Parcelable> Bundle.getParcelables(
    key: String
): ArrayList<T>? {
    return if (VersionUtils.hasT()) {
        getParcelableArrayList(key, T::class.java)
    } else {
        getParcelableArrayList(key)
    }
}