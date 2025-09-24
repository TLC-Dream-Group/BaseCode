package com.appnew.basecode.ui.feature.home

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appnew.basecode.data.local.entities.Banner
import com.appnew.basecode.data.local.entities.Category
import com.appnew.basecode.data.local.entities.Wallpaper
import com.appnew.basecode.data.local.reponsitory.WallpaperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WallpaperRepository
) : ViewModel() {

    private val _banners = MutableLiveData<List<Banner>>()
    val banners: LiveData<List<Banner>> = _banners

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _wallpapers = MutableLiveData<List<Wallpaper>>()
    val wallpapers: LiveData<List<Wallpaper>> = _wallpapers

    private var bannerIndex = 0
    private val handler = Handler(Looper.getMainLooper())

    private val bannerRunnable = object : Runnable {
        override fun run() {
            val current = _banners.value.orEmpty()
            if (current.isNotEmpty()) {
                bannerIndex = (bannerIndex + 1) % current.size
                _banners.postValue(current) // trigger observer
            }
            handler.postDelayed(this, 3500)
        }
    }

    init {
        loadData()
        startBannerRotation()
    }

    private fun loadData() {
        _banners.value = repository.getBanners()
        _categories.value = repository.getCategories()
        _wallpapers.value = repository.getTrending()
    }

    fun filterWallpapers(type: String) {
        _wallpapers.value = when (type) {
            "trending" -> repository.getTrending().shuffled()
            "static"   -> repository.getStatic().shuffled()
            "dynamic"  -> repository.getDynamic().reversed()
            else       -> emptyList()
        }
    }

    private fun startBannerRotation() {
        handler.postDelayed(bannerRunnable, 3500)
    }

    override fun onCleared() {
        handler.removeCallbacksAndMessages(null)
        super.onCleared()
    }
}
