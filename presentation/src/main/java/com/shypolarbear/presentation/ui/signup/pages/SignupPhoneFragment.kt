package com.shypolarbear.presentation.ui.signup.pages

import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.view.KeyEvent
import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupPhoneBinding
import com.shypolarbear.presentation.ui.signup.SignupViewModel
import com.shypolarbear.presentation.util.hideKeyboard
import com.shypolarbear.presentation.util.keyboardDown

val phonePattern = Regex("[^0-9]")
class SignupPhoneFragment :
    BaseFragment<FragmentSignupPhoneBinding, SignupViewModel>(R.layout.fragment_signup_phone) {
    override val viewModel: SignupViewModel by viewModels({ requireParentFragment() })

    override fun initView() {
        binding.apply {
            etSignupPhone.keyboardDown(this@SignupPhoneFragment)

            etSignupPhone.addTextChangedListener(object : PhoneNumberFormattingTextWatcher("KR") {
                override fun afterTextChanged(s: Editable?) {
                    super.afterTextChanged(s)
                    when {
                        s != null && s.length == 13 -> {
                            val phoneNumber = s.replace(phonePattern, "")
                            sendData(phoneNumber)
                        }

                        s.isNullOrEmpty() -> {
                            sendData("")
                        }

                        else -> {
                            sendData("")
                        }
                    }
                }
            })
        }
    }

    private fun sendData(data: String) {
        // 프로필 이미지를 uri나 bitmap/mime Type으로 보내는 것은 추후 설정
        viewModel.setPhoneData(data)
    }
}