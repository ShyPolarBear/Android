package com.shypolarbear.presentation.sample

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentTestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestFragment : BaseFragment<FragmentTestBinding, TestViewModel>(
    R.layout.fragment_test
) {
    override val viewModel: TestViewModel by viewModels()

    override fun initView() {
        binding.apply {

            viewModel.sampleData.observe(viewLifecycleOwner) {
                binding.exampleModel = it
            }
        }
    }
}