package com.shypolarbear.presentation.ui.signup.pages

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupMailBinding
import com.shypolarbear.presentation.ui.signup.SignupViewModel
import com.shypolarbear.presentation.util.afterTextChanged
import com.shypolarbear.presentation.util.emailPattern
import com.shypolarbear.presentation.util.keyboardDown

class SignupMailFragment :
    BaseFragment<FragmentSignupMailBinding, SignupViewModel>(R.layout.fragment_signup_mail) {
    override val viewModel: SignupViewModel by viewModels({ requireParentFragment() })
    override fun initView() {
        binding.apply {
            etSignupMail.keyboardDown(this@SignupMailFragment)

            etSignupMail.afterTextChanged({
                val match :MatchResult? = emailPattern.find(it.toString())
                viewModel.setMailData(match?.value ?: "")
            })
        }
    }
}