package com.appnew.basecode.data.local.reponsitory

import com.appnew.basecode.R
import com.appnew.basecode.data.local.entities.Banner
import com.appnew.basecode.data.local.entities.Category
import com.appnew.basecode.data.local.entities.Wallpaper
import javax.inject.Inject

class WallpaperRepository @Inject constructor() {

    fun getBanners() = listOf(
        Banner(1, R.drawable.sample_banner_1, "COAL BLACK"),
        Banner(2, R.drawable.sample_banner_2, "FIRE ABSTRACT"),
        Banner(3, R.drawable.sample_banner_3, "DARK MODE")
    )

    fun getCategories() = listOf(
        Category(1, "Abstract", R.drawable.sample_banner_1),
        Category(2, "Gaming", R.drawable.sample_banner_1),
        Category(3, "Music", R.drawable.sample_banner_1),
        Category(4, "Girl", R.drawable.sample_banner_1),
        Category(5, "Nature", R.drawable.sample_banner_1)
    )

    fun getTrending() = listOf(
        Wallpaper(1, R.drawable.sample_banner_1),
        Wallpaper(2, R.drawable.b2),
        Wallpaper(3, R.drawable.b3),
        Wallpaper(4, R.drawable.b4),
        Wallpaper(5, R.drawable.b5),
        Wallpaper(6, R.drawable.b6)
    )

    fun getStatic() = listOf(
        Wallpaper(1, R.drawable.h1),
        Wallpaper(2, R.drawable.b6),
        Wallpaper(3, R.drawable.b3),
        Wallpaper(4, R.drawable.b2),
        Wallpaper(5, R.drawable.b4),
        Wallpaper(6, R.drawable.b5)
    )

    fun getDynamic() = List(6) {
        Wallpaper(it + 1, R.drawable.sample_banner_3)
    }
}
