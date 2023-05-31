package com.shypolarbear.presentation

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.shypolarbear.presentation.base.BaseActivity
import com.shypolarbear.presentation.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding,TestViewModel>(R.layout.activity_main) {
    override val viewModel: TestViewModel
        get() = TODO("Not yet implemented")

    override fun initView() {
        binding.apply {
            tvMainTest.text = "base 테스트중"
        }
    }

}