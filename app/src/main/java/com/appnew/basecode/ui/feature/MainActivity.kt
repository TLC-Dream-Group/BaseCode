package com.appnew.basecode.ui.feature

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.appnew.basecode.base.AppBaseActivity
import com.appnew.basecode.common.delegate.binding.viewBinding
import com.appnew.basecode.common.extensions.applySystemBarsInsets
import com.appnew.basecode.databinding.ActivityMainBinding

class MainActivity : AppBaseActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.root.applySystemBarsInsets()
        initView()
        initListener()
        observeSavedData()
    }


    private fun initView() {
    }

    private fun initListener() {
    }

    private fun observeSavedData() {
    }
}