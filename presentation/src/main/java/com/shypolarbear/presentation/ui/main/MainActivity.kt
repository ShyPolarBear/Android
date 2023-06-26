package com.shypolarbear.presentation.ui.main

import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseActivity
import com.shypolarbear.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main
) {
    override val viewModel: MainViewModel by viewModels()

    override fun initView() {
        binding.apply {

        }
    }

    override fun preLoad() {
        installSplashScreen()
    }

}