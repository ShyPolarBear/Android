package com.shypolarbear.presentation.ui.signup.pages

import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupMailBinding
import com.shypolarbear.presentation.databinding.FragmentSignupPhoneBinding
import com.shypolarbear.presentation.ui.signup.SignupViewModel

class SignupPhoneFragment: BaseFragment<FragmentSignupPhoneBinding, SignupViewModel>(R.layout.fragment_signup_phone) {
    override val viewModel: SignupViewModel by viewModels({ requireParentFragment() })

    override fun initView() {
        binding.apply {
            etSignupPhone.addTextChangedListener(object : PhoneNumberFormattingTextWatcher("KR"){
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    super.beforeTextChanged(s, start, count, after)
                }

                override fun afterTextChanged(s: Editable?) {
                    super.afterTextChanged(s)
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    super.onTextChanged(s, start, before, count)
                }
            })
        }
    }
}