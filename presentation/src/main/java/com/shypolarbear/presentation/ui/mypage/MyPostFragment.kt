package com.shypolarbear.presentation.ui.mypage

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentMyPostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPostFragment: BaseFragment<FragmentMyPostBinding, MyPostViewModel>(R.layout.fragment_my_post) {
    override val viewModel: MyPostViewModel by viewModels()

    override fun initView() {

        binding.apply {

        }

    }
}