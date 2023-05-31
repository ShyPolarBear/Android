package com.shypolarbear.presentation.sample

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSampleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SampleFragment: BaseFragment<FragmentSampleBinding, SampleViewModel> (
    R.layout.fragment_sample
) {
    override val viewModel: SampleViewModel by viewModels()

    override fun initView() {
        binding.apply {
            binding.tvSampleTitle.text = "부끄북극"

            viewModel.sampleData.observe(viewLifecycleOwner) {
                binding.exampleModel = it
            }
        }
    }
}
