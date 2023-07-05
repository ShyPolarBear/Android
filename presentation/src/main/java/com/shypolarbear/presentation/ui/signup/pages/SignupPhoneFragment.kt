package com.shypolarbear.presentation.ui.signup.pages

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupMailBinding
import com.shypolarbear.presentation.databinding.FragmentSignupPhoneBinding
import com.shypolarbear.presentation.ui.signup.SignupViewModel

class SignupPhoneFragment: BaseFragment<FragmentSignupPhoneBinding, SignupViewModel>(R.layout.fragment_signup_phone) {
    override val viewModel: SignupViewModel by viewModels({ requireParentFragment() })

    override fun initView() {
    }
}