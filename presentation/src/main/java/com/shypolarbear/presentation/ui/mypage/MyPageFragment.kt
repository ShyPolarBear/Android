package com.shypolarbear.presentation.ui.mypage

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentMyPageBinding
import com.shypolarbear.presentation.util.detectActivation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding, MyPageViewModel>(R.layout.fragment_my_page) {
    override val viewModel: MyPageViewModel by viewModels()

    override fun initView() {

        binding.apply {
            tvMyPostPost.isActivated = true
            val myPostCategory = listOf(tvMyPostPost, tvMyPostComment)
            myPostCategory.map { choice ->
                choice.setOnClickListener {
                    // 선택된 옵션에 따라 rv 아이템 변경
                    choice.detectActivation(*myPostCategory.filter { other ->
                        other != choice
                    }.toTypedArray())
                }
            }

            myPostBtnBack.setOnClickListener {
                findNavController().navigate(R.id.action_myPageFragment_to_navigation_more)
            }





        }

    }
}