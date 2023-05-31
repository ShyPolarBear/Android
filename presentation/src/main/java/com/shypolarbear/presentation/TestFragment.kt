package com.shypolarbear.presentation

import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentTestBinding

class TestFragment : BaseFragment<FragmentTestBinding, TestViewModel>(
    R.layout.fragment_test
) {
    override val viewModel: TestViewModel
        get() = TODO("Not yet implemented")

    override fun initView() {
        TODO("Not yet implemented")
    }
}