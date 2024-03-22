package com.beeeam.navigation

import androidx.fragment.app.viewModels
import com.beeeam.base.BaseFragment
import com.beeeam.navigation.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(
    R.layout.fragment_main,
) {

    override val viewModel: MainViewModel by viewModels()

    override fun initView() {
        binding.apply {
        }
    }
}
