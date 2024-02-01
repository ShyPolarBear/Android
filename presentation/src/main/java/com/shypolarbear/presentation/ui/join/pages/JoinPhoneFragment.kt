package com.shypolarbear.presentation.ui.join.pages

import android.telephony.PhoneNumberFormattingTextWatcher
import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupPhoneBinding
import com.shypolarbear.presentation.ui.join.JoinViewModel
import com.shypolarbear.presentation.util.InputState
import com.shypolarbear.presentation.util.afterTextChanged
import com.shypolarbear.presentation.util.keyboardDown
import com.shypolarbear.presentation.util.phonePattern
import com.shypolarbear.presentation.util.setColorStateWithInput
import dagger.hilt.android.AndroidEntryPoint

const val PHONE_NUMBER_DASH_INCLUDE = 13

@AndroidEntryPoint
class JoinPhoneFragment :
    BaseFragment<FragmentSignupPhoneBinding, JoinViewModel>(R.layout.fragment_signup_phone) {
    override val viewModel: JoinViewModel by viewModels({ requireParentFragment() })
    private lateinit var phoneNumber: String
    override fun initView() {
        binding.apply {
            etSignupPhone.apply {
                keyboardDown(this@JoinPhoneFragment)
                addTextChangedListener(PhoneNumberFormattingTextWatcher("KR"))
                afterTextChanged { s ->
                    val state = when {
                        s.isNullOrEmpty() -> {
                            tvSignupPhoneRule.text = getString(R.string.signup_phone_hint_detail)
                            InputState.ON
                        }

                        s.length == PHONE_NUMBER_DASH_INCLUDE -> {
                            phoneNumber = s.replace(phonePattern, "")
                            tvSignupPhoneRule.text = getString(R.string.signup_phone_hint_confirm)
                            InputState.ACCEPT
                        }

                        else -> {
                            phoneNumber = ""
                            tvSignupPhoneRule.text = getString(R.string.signup_phone_hint_error)
                            InputState.ERROR
                        }
                    }
                    setColorStateWithInput(
                        state,
                        tvSignupPhoneRule,
                        signupEtCheck,
                    )
                    viewModel.setPhoneData(phoneNumber)
                }
            }
        }
    }
}
