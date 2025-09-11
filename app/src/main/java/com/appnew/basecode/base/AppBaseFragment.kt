package com.appnew.basecode.base

import android.content.Intent
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class AppBaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    fun showAds(
        intent: Intent? = null,
        isBackScreen: Boolean = false,
        isFinish: Boolean = false,
        immersiveMode: Boolean = false
    ) {
        (activity as? AppBaseActivity?)?.showAdsExternal(
            intent, isBackScreen, isFinish, immersiveMode
        ) ?: run {
            if (intent != null) {
                startActivity(intent)
            }
        }
    }
}