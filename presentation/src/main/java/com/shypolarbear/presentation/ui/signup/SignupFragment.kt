package com.shypolarbear.presentation.ui.signup

import android.view.View.OnTouchListener
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupBinding
import com.shypolarbear.presentation.ui.signup.pages.SignupNameFragment
import com.shypolarbear.presentation.ui.signup.pages.SignupTermsFragment

class SignupFragment :
    BaseFragment<FragmentSignupBinding, SignupViewModel>(R.layout.fragment_signup) {

    override val viewModel: SignupViewModel by viewModels()

    override fun initView() {

//        childFragmentManager.beginTransaction().replace(R.id.signup_fragment, SignupTermsFragment()).commit()
        childFragmentManager.beginTransaction().replace(R.id.signup_fragment, SignupNameFragment()).commit()
//        childFragmentManager.beginTransaction().replace(R.id.signup_fragment, SignupFragment()).commit()
//        childFragmentManager.beginTransaction().replace(R.id.signup_fragment, SignupTermsFragment()).commit()

        binding.apply {
            signupBtnNext.setOnClickListener {
                //조건을 갖추면 활성화 되도록
                binding.signupBtnNext.isActivated = true
                binding.signupTvNext.isActivated = true
            }
        }
    }
}