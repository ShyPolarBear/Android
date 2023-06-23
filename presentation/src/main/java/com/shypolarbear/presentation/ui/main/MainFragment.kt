package com.shypolarbear.presentation.ui.main

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: BaseFragment<FragmentMainBinding, MainViewModel>(
    R.layout.fragment_main
) {

    override val viewModel: MainViewModel by viewModels()

    override fun initView() {
        binding.apply {

        }
    }
}