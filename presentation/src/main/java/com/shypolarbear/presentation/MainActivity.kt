package com.shypolarbear.presentation

import androidx.activity.viewModels
import com.shypolarbear.presentation.base.BaseActivity
import com.shypolarbear.presentation.databinding.ActivityMainBinding
import com.shypolarbear.presentation.sample.TestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, TestViewModel>(R.layout.activity_main) {

    override val viewModel: TestViewModel by viewModels()

    override fun initView() {
        binding.apply {

        }
    }

}