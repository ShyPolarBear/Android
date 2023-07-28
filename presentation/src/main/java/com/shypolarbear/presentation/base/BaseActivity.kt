package com.shypolarbear.presentation.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B: ViewDataBinding, VM: BaseViewModel>(
    @LayoutRes private val layoutId: Int
):  AppCompatActivity(){

    protected lateinit var binding: B
    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadSplashScreen()

        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this

        initView()
    }
    
    protected abstract fun initView()

    private fun loadSplashScreen() {
        installSplashScreen()
    }
}