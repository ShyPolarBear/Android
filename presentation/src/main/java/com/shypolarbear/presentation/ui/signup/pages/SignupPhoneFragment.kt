package com.shypolarbear.presentation.ui.signup.pages

import android.telephony.PhoneNumberFormattingTextWatcher
import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupPhoneBinding
import com.shypolarbear.presentation.ui.signup.SignupViewModel
import com.shypolarbear.presentation.util.afterTextChanged
import com.shypolarbear.presentation.util.keyboardDown
import com.shypolarbear.presentation.util.phonePattern

class SignupPhoneFragment :
    BaseFragment<FragmentSignupPhoneBinding, SignupViewModel>(R.layout.fragment_signup_phone) {
    override val viewModel: SignupViewModel by viewModels({ requireParentFragment() })
    override fun initView() {
        binding.apply {
            etSignupPhone.keyboardDown(this@SignupPhoneFragment)

            etSignupPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher("KR"))
            etSignupPhone.afterTextChanged({ s ->
                viewModel.setPhoneData("")
                s?.let {
                    if(s.length == 13){
                        val phoneNumber = s.replace(phonePattern, "")
                        viewModel.setPhoneData(phoneNumber)
                    }
                }
            })
        }
    }
}