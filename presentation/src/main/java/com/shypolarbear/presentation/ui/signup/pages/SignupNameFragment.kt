package com.shypolarbear.presentation.ui.signup.pages

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupNameBinding
import com.shypolarbear.presentation.ui.signup.SignupViewModel
import com.shypolarbear.presentation.util.hideKeyboard
import com.shypolarbear.presentation.util.setTextColorById

class SignupNameFragment :
    BaseFragment<FragmentSignupNameBinding, SignupViewModel>(R.layout.fragment_signup_name) {
    override val viewModel: SignupViewModel by viewModels({ requireParentFragment() })

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {

        binding.apply {
            layoutSignupName.setOnTouchListener { v, _ ->
                hideKeyboard()
                tvSignupNameRule.setTextColorById(requireContext(), R.color.Gray_05)
                v.clearFocus()
                false
            }
            ivSignupNameEdit.setOnClickListener {
                etSignupNickname.clearFocus()
            }

            etSignupNickname.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    tvSignupNameRule.setTextColorById(requireContext(), R.color.Blue_02)
                }
            }

            etSignupNickname.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                    tvSignupNameRule.text = getString(R.string.signup_check_text)
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    tvSignupNameRule.setTextColorById(requireContext(), R.color.Blue_02)
                }

                override fun afterTextChanged(s: Editable?) {
                    when {
                        s != null && s.length !in 2..8 -> {
                            tvSignupNameRule.text = getString(R.string.singup_error_text)
                            tvSignupNameRule.setTextColorById(requireContext(), R.color.Error_01)
                            sendData(s.toString())
                        }

                        s.isNullOrEmpty() -> {
                            tvSignupNameRule.text = getString(R.string.signup_name_rule)
                            tvSignupNameRule.setTextColorById(requireContext(), R.color.Blue_02)
                            sendData(s.toString())
                        }

                        else -> {
                            tvSignupNameRule.text = getString(R.string.signup_confirm_text)
                            tvSignupNameRule.setTextColorById(requireContext(), R.color.Success_01)
                            sendData(s.toString())
                        }
                    }
                }
            })
        }
    }

    private fun sendData(data: String) {
        viewModel.setNameData(data)
    }

}