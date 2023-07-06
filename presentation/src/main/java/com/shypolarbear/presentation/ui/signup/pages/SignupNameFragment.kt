package com.shypolarbear.presentation.ui.signup.pages

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupNameBinding
import com.shypolarbear.presentation.ui.signup.SignupViewModel
import com.shypolarbear.presentation.ui.signup.custom.CustomFunctions.hideKeyboard
import com.shypolarbear.presentation.ui.signup.custom.CustomFunctions.setTextColor

class SignupNameFragment :
    BaseFragment<FragmentSignupNameBinding, SignupViewModel>(R.layout.fragment_signup_name) {
    override val viewModel: SignupViewModel by viewModels({ requireParentFragment() })

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {

        binding.apply {
            layoutSignupName.setOnTouchListener { v, _ ->
                hideKeyboard()
                setTextColor(requireContext(),tvSignupNameRule, R.color.Gray_05)
                v.clearFocus()
                false
            }
            ivSignupNameEdit.setOnClickListener {
                etSignupNickname.clearFocus()
            }

            etSignupNickname.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    setTextColor(requireContext(),tvSignupNameRule, R.color.Blue_02)
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
                    setTextColor(requireContext(),tvSignupNameRule, R.color.Blue_02)
                }

                override fun afterTextChanged(s: Editable?) {
                    if (s!!.length !in 8 downTo 2) {
                        tvSignupNameRule.text = getString(R.string.singup_error_text)
                        setTextColor(requireContext(),tvSignupNameRule, R.color.Error_01)
                    } else {
                        tvSignupNameRule.text = getString(R.string.signup_confirm_text)
                        setTextColor(requireContext(), tvSignupNameRule, R.color.Success_01)
                    }
                    if (s.isEmpty()) {
                        tvSignupNameRule.text = getString(R.string.signup_name_rule)
                        setTextColor(requireContext(), tvSignupNameRule, R.color.Blue_02)
                    }
                }
            })
        }
    }

}