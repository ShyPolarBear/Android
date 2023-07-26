package com.shypolarbear.presentation.ui.main

import android.view.View
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseActivity
import com.shypolarbear.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main
) {
    override val viewModel: MainViewModel by viewModels()

    private lateinit var navController: NavController

    override fun initView() {
        binding.apply {
            // LoginFragment 테스트
             bottomNavigationBar.visibility = View.INVISIBLE
        }

        initNavBar()
    }

    private fun initNavBar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment1) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationBar.itemIconTintList = null

        binding.bottomNavigationBar.setupWithNavController(navController)
        binding.bottomNavigationBar.setOnItemReselectedListener { }
    }

    override fun preLoad() {
        installSplashScreen()
    }

}