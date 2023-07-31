package com.shypolarbear.presentation.ui.signup.pages

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupTermsBinding
import com.shypolarbear.presentation.ui.signup.SignupViewModel

class SignupTermsFragment :
    BaseFragment<FragmentSignupTermsBinding, SignupViewModel>(R.layout.fragment_signup_terms) {

    override val viewModel: SignupViewModel by viewModels({ requireParentFragment() })

    override fun initView() {
        binding.apply {
            val checkList = listOf(signupTermsCbTerms, signupTermsCbPrivacy, signupTermsCbAge)
            val isComplete = arrayListOf(false, false, false)

            signupTermsCbAll.setOnCheckedChangeListener { buttonView, isChecked ->
                buttonView.setOnClickListener {
                    if (buttonView.isChecked) {
                        for ((idx, i) in checkList.withIndex()) {
                            i.isChecked = true
                            isComplete[idx] = true
                        }
                        viewModel.setTermData(true)
                    } else {
                        for (i in checkList) {
                            i.isChecked = false
                        }
                        viewModel.setTermData(false)
                    }
                }
            }

            signupTermsCbPrivacy.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    isComplete[0] = true
                    if (false !in isComplete) {
                        signupTermsCbAll.isChecked = true
                        viewModel.setTermData(true)
                    }
                } else {
                    signupTermsCbAll.isChecked = false
                    isComplete[0] = false
                    viewModel.setTermData(false)
                }
            }

            signupTermsCbTerms.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    isComplete[1] = true
                    if (false !in isComplete) {
                        signupTermsCbAll.isChecked = true
                        viewModel.setTermData(true)
                    }
                } else {
                    signupTermsCbAll.isChecked = false
                    isComplete[1] = false
                    viewModel.setTermData(false)
                }
            }

            signupTermsCbAge.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    isComplete[2] = true
                    if (false !in isComplete) {
                        signupTermsCbAll.isChecked = true
                        viewModel.setTermData(true)
                    }
                } else {
                    signupTermsCbAll.isChecked = false
                    isComplete[2] = false
                    viewModel.setTermData(false)
                }
            }
        }
    }
}