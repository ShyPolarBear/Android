package com.shypolarbear.presentation

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentMainBinding

class MainFragment: BaseFragment<FragmentMainBinding, MainViewModel>(
    R.layout.fragment_main
) {

    override val viewModel: MainViewModel by viewModels()

    override fun initView() {
        binding.apply {
            binding.text1.text = "부끄북극"
        }
    }
}