package com.appnew.basecode.ui.feature

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.appnew.basecode.R
import com.appnew.basecode.base.AppBaseActivity
import com.appnew.basecode.common.delegate.binding.viewBinding
import com.appnew.basecode.common.extensions.applySystemBarsInsets
import com.appnew.basecode.data.local.entities.Banner
import com.appnew.basecode.data.local.entities.Category
import com.appnew.basecode.data.local.entities.Wallpaper
import com.appnew.basecode.databinding.ActivityMainBinding
import com.appnew.basecode.ui.adapter.BannerAdapter
import com.appnew.basecode.ui.adapter.CategoryAdapter
import com.appnew.basecode.ui.adapter.WallpaperAdapter

class MainActivity : AppBaseActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private val bannerHandler = Handler(Looper.getMainLooper())
    private var bannerIndex = 0

    private val banners by lazy {
        listOf(
            Banner(1, R.drawable.sample_banner_1, "COAL BLACK"),
            Banner(2, R.drawable.sample_banner_2, "FIRE ABSTRACT"),
            Banner(3, R.drawable.sample_banner_3, "DARK MODE")
        )
    }

    private val categories by lazy {
        listOf(
            Category(1, "Abstract", R.drawable.sample_banner_1),
            Category(2, "Gaming",  R.drawable.sample_banner_1),
            Category(3, "Music",   R.drawable.sample_banner_1),
            Category(4, "Girl",    R.drawable.sample_banner_1),
            Category(5, "Nature",  R.drawable.sample_banner_1)
        )
    }

    private val allTrending by lazy {
        listOf(
            Wallpaper(1, R.drawable.sample_banner_1),
            Wallpaper(2, R.drawable.b2),
            Wallpaper(3, R.drawable.b3),
            Wallpaper(4, R.drawable.b4),
            Wallpaper(5, R.drawable.b5),
            Wallpaper(6, R.drawable.b6)
        )
    }
    private val allStatic by lazy {
        listOf(
            Wallpaper(1, R.drawable.h1),
            Wallpaper(2, R.drawable.b6),
            Wallpaper(3, R.drawable.b3),
            Wallpaper(4, R.drawable.b2),
            Wallpaper(5, R.drawable.b4),
            Wallpaper(6, R.drawable.b5)
        )
    }
    private val allDynamic by lazy {
        listOf(
            Wallpaper(1, R.drawable.sample_banner_3),
            Wallpaper(2, R.drawable.sample_banner_3),
            Wallpaper(3, R.drawable.sample_banner_3),
            Wallpaper(4, R.drawable.sample_banner_3),
            Wallpaper(5, R.drawable.sample_banner_3),
            Wallpaper(6, R.drawable.sample_banner_3)
        )
    }

    private lateinit var wallpaperAdapter: WallpaperAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.root.applySystemBarsInsets()

        initView()
        initListener()
        observeSavedData()

        setupBanner()
        setupCategory()
        setupWallpaperGrid()
        setupChips()
        binding.bottomNav.setOnItemSelectedListener { true }
    }

    private fun setupBanner() {
        binding.viewPagerBanner.adapter = BannerAdapter(banners)
        binding.viewPagerBanner.offscreenPageLimit = 1

        val task = object : Runnable {
            override fun run() {
                if (banners.isNotEmpty()) {
                    bannerIndex = (bannerIndex + 1) % banners.size
                    binding.viewPagerBanner.setCurrentItem(bannerIndex, true)
                }
                bannerHandler.postDelayed(this, 3500)
            }
        }
        bannerHandler.postDelayed(task, 3500)
    }

    private fun setupCategory() {
        binding.rvCategory.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = CategoryAdapter(categories) { cat ->
                // TODO: lọc theo category
            }
        }
    }

    private fun setupWallpaperGrid() {
        // Khởi tạo adapter với list rỗng, nạp dữ liệu sau cho an toàn
        wallpaperAdapter = WallpaperAdapter(
            items = mutableListOf(),
            onFav = { /* TODO: toggle & lưu favorite */ },
            onClick = { /* TODO: mở preview fullscreen */ }
        )
        binding.rvWallpapers.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            adapter = wallpaperAdapter
        }

        // Lần đầu: mặc định Trending (đã tick trong XML) → submit luôn
        wallpaperAdapter.submit(allTrending.shuffled())
    }


    private fun setupChips() {
        binding.chipGroup.setOnCheckedStateChangeListener { _, _ ->
            when {
                binding.chipTrending.isChecked -> {
                    wallpaperAdapter.submit(allTrending.shuffled())
                }
                binding.chipStatic.isChecked -> {
                    wallpaperAdapter.submit(allStatic.shuffled())
                }
                binding.chipDynamic.isChecked -> {
                    wallpaperAdapter.submit(allDynamic.reversed())
                }
            }
        }
    }

    override fun onDestroy() {
        bannerHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    private fun initView() {}
    private fun initListener() {}
    private fun observeSavedData() {}
}
