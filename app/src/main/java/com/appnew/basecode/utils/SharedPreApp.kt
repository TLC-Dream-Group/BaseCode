package com.appnew.basecode.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefApp(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE)

    var isDemoApp: Boolean
        get() = sharedPreferences.getBoolean(Constants.DEMO_APP, false)
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(Constants.DEMO_APP, value)
                apply()
            }
        }
    var language: String?
        get() = sharedPreferences.getString(Constants.LANGUAGE, null)
        set(value) {
            sharedPreferences.edit().apply {
                putString(Constants.LANGUAGE, value)
                apply()
            }
        }
}
