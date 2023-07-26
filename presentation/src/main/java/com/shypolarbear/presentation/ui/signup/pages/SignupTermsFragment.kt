package com.shypolarbear.presentation.ui.signup.pages

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupTermsBinding
import com.shypolarbear.presentation.ui.signup.SignupViewModel

class SignupTermsFragment: BaseFragment<FragmentSignupTermsBinding, SignupViewModel>(R.layout.fragment_signup_terms) {

    override val viewModel: SignupViewModel by viewModels()

    override fun initView() {
        binding.apply {
            val checkList = listOf(signupTermsCbTerms,signupTermsCbPrivacy,signupTermsCbAge)

            signupTermsCbAll.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    for(i in checkList) {
                        i.isChecked = true
                    }
                    sendData("true")
                }else{
                    for(i in checkList) {
                        i.isChecked = false
                    }
                }
            }
        }
    }
    private fun sendData(data: String) {
        viewModel.setTermData(data)
    }
}