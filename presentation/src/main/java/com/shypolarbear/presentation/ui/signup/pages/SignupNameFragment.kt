package com.shypolarbear.presentation.ui.signup.pages

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupNameBinding
import com.shypolarbear.presentation.ui.signup.NAME_RANGE
import com.shypolarbear.presentation.ui.signup.SignupViewModel
import com.shypolarbear.presentation.util.GlideUtil
import com.shypolarbear.presentation.util.InputState
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

            etSignupNickname.apply {
                keyboardDown(this@SignupNameFragment)
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int,
                    ) {
                        setColorStateWithInput(
                            InputState.ON,
                            tvSignupNameRule,
                            signupEtCheck
                        )
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int,
                    ) {
                        setColorStateWithInput(
                            InputState.ON,
                            tvSignupNameRule,
                            signupEtCheck
                        )
                    }

                    override fun afterTextChanged(s: Editable?) {
                        val state = when {
                            s.isNullOrEmpty() -> {
                                tvSignupNameRule.text = getString(R.string.signup_name_rule)
                                InputState.ON
                            }

                            s.length !in NAME_RANGE -> {
                                tvSignupNameRule.text = getString(R.string.singup_error_text)
                                InputState.ERROR
                            }

                            else -> {
                                tvSignupNameRule.text = getString(R.string.signup_confirm_text)
                                InputState.ACCEPT
                            }
                        }

                        setColorStateWithInput(
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
}