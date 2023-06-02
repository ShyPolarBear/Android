package com.shypolarbear.presentation

import com.shypolarbear.presentation.base.BaseActivity
import com.shypolarbear.presentation.databinding.ActivityMainBinding
import com.shypolarbear.presentation.sample.TestViewModel

class MainActivity : BaseActivity<ActivityMainBinding, TestViewModel>(R.layout.activity_main) {
    override val viewModel: TestViewModel
        get() = TODO("Not yet implemented")

    override fun initView() {
        binding.apply {
            tvMainTest.text = "base 테스트중"
        }
    }

}