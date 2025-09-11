package com.appnew.basecode.base

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.appnew.basecode.common.helper.LocaleHelper
import com.appnew.basecode.utils.SharedPrefApp

abstract class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        val pref = SharedPrefApp(newBase)
        val lang = pref.language ?: "en"
        val context = LocaleHelper.updateLocale(newBase, lang)
        super.attachBaseContext(context)
    }
}

