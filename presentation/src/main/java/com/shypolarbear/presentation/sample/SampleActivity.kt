package com.shypolarbear.presentation.sample

import android.os.Bundle
import androidx.activity.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseActivity
import com.shypolarbear.presentation.databinding.ActivitySampleBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SampleActivity : BaseActivity<ActivitySampleBinding, SampleViewModel>(
    R.layout.activity_sample
) {
    override val viewModel: SampleViewModel by viewModels()

    override fun initView() {
        binding.apply {
            Timber.d("Timber Test(Activity)")
        }
    }
}

