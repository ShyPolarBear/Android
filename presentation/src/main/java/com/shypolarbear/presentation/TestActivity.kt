package com.shypolarbear.presentation

import com.shypolarbear.presentation.base.BaseActivity
import com.shypolarbear.presentation.databinding.ActivityTestBinding

class TestActivity : BaseActivity<ActivityTestBinding, TestViewModel>(R.layout.activity_test) {
    override val viewModel: TestViewModel
        get() = TODO("Not yet implemented")

    override fun initView() {
        TODO("Not yet implemented")
    }
}