package com.appnew.basecode.ui.feature.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.appnew.basecode.base.AppBaseActivity
import com.appnew.basecode.common.delegate.binding.viewBinding
import com.appnew.basecode.common.extensions.applySystemBarsInsets
import com.appnew.basecode.databinding.ActivityMainBinding
import com.appnew.basecode.ui.adapter.BannerAdapter
import com.appnew.basecode.ui.adapter.CategoryAdapter
import com.appnew.basecode.ui.adapter.WallpaperAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppBaseActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: MainViewModel by viewModels()

    private lateinit var wallpaperAdapter: WallpaperAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.root.applySystemBarsInsets()

        initView()
        initObserver()
        initListener()
    }

    private fun initView() {
        binding.viewPagerBanner.offscreenPageLimit = 1

        binding.rvCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        wallpaperAdapter = WallpaperAdapter(
            items = mutableListOf(),
            onFav = { /* TODO */ },
            onClick = { /* TODO */ }
        )
        binding.rvWallpapers.layoutManager = GridLayoutManager(this, 3)
        binding.rvWallpapers.adapter = wallpaperAdapter
    }

    private fun initObserver() {
        viewModel.banners.observe(this) { banners ->
            binding.viewPagerBanner.adapter = BannerAdapter(banners)
        }

        viewModel.categories.observe(this) { cats ->
            binding.rvCategory.adapter = CategoryAdapter(cats) { cat ->
                // TODO: lọc theo category
            }
        }

        viewModel.wallpapers.observe(this) { walls ->
            wallpaperAdapter.submit(walls)
        }
    }

    private fun initListener() {
        binding.chipGroup.setOnCheckedStateChangeListener { _, _ ->
            when {
                binding.chipTrending.isChecked -> viewModel.filterWallpapers("trending")
                binding.chipStatic.isChecked -> viewModel.filterWallpapers("static")
                binding.chipDynamic.isChecked -> viewModel.filterWallpapers("dynamic")
            }
        }

        binding.bottomNav.setOnItemSelectedListener { true }
    }
}
