package com.beeeam.signup.pages

import android.telephony.PhoneNumberFormattingTextWatcher
import androidx.fragment.app.viewModels
import com.beeeam.base.BaseFragment
import com.beeeam.signup.JoinViewModel
import com.beeeam.signup.R
import com.beeeam.signup.databinding.FragmentSignupPhoneBinding
import com.beeeam.util.Const.PHONE_NUMBER_DASH_INCLUDE
import com.beeeam.util.InputState
import com.beeeam.util.afterTextChanged
import com.beeeam.util.keyboardDown
import com.beeeam.util.phonePattern
import com.beeeam.util.setColorStateWithInput
import dagger.hilt.android.AndroidEntryPoint

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
                            tvSignupPhoneRule.text = getString(com.beeeam.designsystem.R.string.signup_phone_hint_detail)
                            InputState.ON
                        }

                        s.length == PHONE_NUMBER_DASH_INCLUDE -> {
                            phoneNumber = s.replace(phonePattern, "")
                            tvSignupPhoneRule.text = getString(com.beeeam.designsystem.R.string.signup_phone_hint_confirm)
                            InputState.ACCEPT
                        }

                        else -> {
                            phoneNumber = ""
                            tvSignupPhoneRule.text = getString(com.beeeam.designsystem.R.string.signup_phone_hint_error)
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
