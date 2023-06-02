package com.shypolarbear.presentation.sample

import com.shypolarbear.presentation.R
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