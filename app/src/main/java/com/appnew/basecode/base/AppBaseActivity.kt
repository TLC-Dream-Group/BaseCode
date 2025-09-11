package com.appnew.basecode.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appnew.basecode.common.extensions.hideStatusBar

abstract class AppBaseActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
    }

    fun showAdsExternal(
        intent: Intent? = null,
        isBackScreen: Boolean = false,
        isFinish: Boolean = false,
        immersiveMode: Boolean = false
    ) {
//        super.showAds(intent, isBackScreen, isFinish, immersiveMode)
    }
}