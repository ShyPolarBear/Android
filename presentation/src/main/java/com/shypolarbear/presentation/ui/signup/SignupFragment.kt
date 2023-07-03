package com.shypolarbear.presentation.ui.signup

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupBinding

class SignupFragment :
    BaseFragment<FragmentSignupBinding, SignupViewModel>(R.layout.fragment_signup) {

    override val viewModel: SignupViewModel by viewModels()

    override fun initView() {
        // 뷰페이저로 회원가입 구현
    }
}