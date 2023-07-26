package com.shypolarbear.presentation.ui.signup.pages

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupTermsBinding
import com.shypolarbear.presentation.ui.signup.SignupFragment
import com.shypolarbear.presentation.ui.signup.SignupViewModel
import timber.log.Timber

class SignupTermsFragment: BaseFragment<FragmentSignupTermsBinding, SignupViewModel>(R.layout.fragment_signup_terms) {

    override val viewModel: SignupViewModel by viewModels({ requireParentFragment() })

    override fun initView() {
        binding.apply {
            
        }
    }
    private fun sendDataToParent(data: String) {
        viewModel.setTermData(data)
    }
}