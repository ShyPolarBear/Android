package com.shypolarbear.presentation.ui.signup.pages

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupNameBinding
import com.shypolarbear.presentation.ui.signup.SignupViewModel


class SignupNameFragment :
    BaseFragment<FragmentSignupNameBinding, SignupViewModel>(R.layout.fragment_signup_name) {
    override val viewModel: SignupViewModel by viewModels({ requireParentFragment() })

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {

        binding.apply {
            layoutSignupName.setOnTouchListener { v, _ ->
                hideKeyboard()
                customSetTextColor(tvSignupNameRule, R.color.Gray_05)
                v.clearFocus()
                false
            }
            ivSignupNameEdit.setOnClickListener {
                etSignupNickname.clearFocus()
            }

            etSignupNickname.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    customSetTextColor(tvSignupNameRule, R.color.Blue_02)
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
                    customSetTextColor(tvSignupNameRule, R.color.Blue_02)
                }

                override fun afterTextChanged(s: Editable?) {
                    if (s!!.length !in 8 downTo 2) {
                        tvSignupNameRule.text = getString(R.string.singup_error_text)
                        customSetTextColor(tvSignupNameRule, R.color.Error_01)
                    } else {
                        tvSignupNameRule.text = getString(R.string.signup_confirm_text)
                        customSetTextColor(tvSignupNameRule, R.color.Success_01)
                    }
                    if (s.isEmpty()) {
                        tvSignupNameRule.text = getString(R.string.signup_name_rule)
                        customSetTextColor(tvSignupNameRule, R.color.Blue_02)
                    }
                }
            })
        }
    }
    private fun customSetTextColor(v: TextView, color: Int){
        v.setTextColor(ContextCompat.getColor(
            requireContext(),
            color
        ))
    }
    private fun hideKeyboard() {
        if (activity != null && requireActivity().currentFocus != null) {
            val inputManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                requireActivity().currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}