package com.shypolarbear.presentation.ui.signup.pages

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupNameBinding
import com.shypolarbear.presentation.ui.signup.InputState
import com.shypolarbear.presentation.ui.signup.NAME_RANGE
import com.shypolarbear.presentation.ui.signup.SignupViewModel
import com.shypolarbear.presentation.util.GlideUtil
import com.shypolarbear.presentation.util.keyboardDown
import com.shypolarbear.presentation.util.setColorStateWithInput
import com.shypolarbear.presentation.util.setTextColorById

class SignupNameFragment :
    BaseFragment<FragmentSignupNameBinding, SignupViewModel>(R.layout.fragment_signup_name) {
    override val viewModel: SignupViewModel by viewModels({ requireParentFragment() })

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                GlideUtil.loadCircleImage(requireContext(), uri, binding.ivSignupNameProfile)
            }
        }

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {

        binding.apply {
            ivSignupImgEdit.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            etSignupNickname.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    tvSignupNameRule.setTextColorById(requireContext(), R.color.Blue_02)
                } else {
                }
            }

            etSignupNickname.keyboardDown(this@SignupNameFragment)
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
                    etSignupNickname.setColorStateWithInput(
                        InputState.ON,
                        tvSignupNameRule,
                        signupEtCheck
                    )
                }

                override fun afterTextChanged(s: Editable?) {
                    val state = when {
                        s != null && s.length !in NAME_RANGE -> {
                            tvSignupNameRule.text = getString(R.string.singup_error_text)
                            InputState.ERROR
                        }

                        s.isNullOrEmpty() -> {
                            tvSignupNameRule.text = getString(R.string.signup_name_rule)
                            InputState.ON
                        }

                        else -> {
                            tvSignupNameRule.text = getString(R.string.signup_confirm_text)
                            InputState.ACCEPT
                        }
                    }
                    etSignupNickname.setColorStateWithInput(
                        state,
                        tvSignupNameRule,
                        signupEtCheck
                    )
                    viewModel.setNameData(s.toString())
                }
            })
        }
    }
}