package com.appnew.basecode.data.local.entities

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ItemData(
    @DrawableRes val imageRes: Int,
    @StringRes val title: Int,
    @StringRes val subtitle: Int,
)

