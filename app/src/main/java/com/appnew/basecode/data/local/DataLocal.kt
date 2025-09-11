package com.appnew.basecode.data.local

import com.appnew.basecode.R
import com.appnew.basecode.data.local.entities.ItemData


enum class FunctionType {
    Type1,
    Type2
}

object DataLocal {
    val FunctionVoiceLock = listOf(
        ItemData(
            imageRes = R.drawable.shape_ripple_circle,
            title = R.string.share,
            subtitle = R.string.share,
        )
    )
}
