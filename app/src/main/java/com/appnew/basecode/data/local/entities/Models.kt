package com.appnew.basecode.data.local.entities

data class Category(
    val id: Int,
    val name: String,
    val iconRes: Int
)

data class Wallpaper(
    val id: Int,
    val imageRes: Int,
    var favorite: Boolean = false
)

data class Banner(
    val id: Int,
    val imageRes: Int,
    val title: String = ""
)