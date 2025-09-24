package com.appnew.basecode.app

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {
        lateinit var application: BaseApplication
        val context: Context get() = application.applicationContext

        fun isProVersion(): Boolean {
            return true
//            return BillingHelper.isUpgraded()
        }
    }
}