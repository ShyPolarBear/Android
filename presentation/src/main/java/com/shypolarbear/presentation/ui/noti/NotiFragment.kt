package com.shypolarbear.presentation.ui.noti

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentNotiBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotiFragment: BaseFragment<FragmentNotiBinding, NotiViewModel>(R.layout.fragment_noti){
    override val viewModel: NotiViewModel by viewModels()

    override fun initView() {
        binding.apply {

        }
    }
}