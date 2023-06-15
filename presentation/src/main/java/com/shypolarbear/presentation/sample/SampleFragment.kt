package com.shypolarbear.presentation.sample

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSampleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SampleFragment: BaseFragment<FragmentSampleBinding, SampleViewModel> (
    R.layout.fragment_sample
) {
    override val viewModel: SampleViewModel by viewModels()

    override fun initView() {
        binding.apply {
            Timber.d("Timber Test(Fragment)")

            binding.tvSampleTitle.text = "부끄북극"

            viewModel.loadSampleData()

            lifecycleScope.launch {
                viewModel.sampleState.collect {
                    binding.pgSample.isVisible = it.loading
                    binding.tvSampleErrorMsg.isVisible = it.error
                    binding.tvSampleData.text = it.category.toString()
                }
            }
        }
    }
}
