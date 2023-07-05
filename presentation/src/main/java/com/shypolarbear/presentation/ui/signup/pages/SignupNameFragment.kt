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


class SignupNameFragment :
    BaseFragment<FragmentSignupNameBinding, SignupViewModel>(R.layout.fragment_signup_name) {
    override val viewModel: SignupViewModel by viewModels({ requireParentFragment() })

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {

        binding.apply {
            layoutSignupName.setOnTouchListener { v, _ ->
                hideKeyboard()
                v.clearFocus()
                false
            }
            ivSignupNameEdit.setOnClickListener {
                etSignupNickname.clearFocus()
            }

            etSignupNickname.setOnFocusChangeListener { v, hasFocus ->
                tvSignupNameRule.isActivated = hasFocus
            }

            etSignupNickname.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }
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