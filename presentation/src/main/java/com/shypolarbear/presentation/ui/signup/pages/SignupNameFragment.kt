package com.shypolarbear.presentation.ui.signup.pages

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupNameFragment :
    BaseFragment<FragmentSignupNameBinding, SignupViewModel>(R.layout.fragment_signup_name) {
    override val viewModel: SignupViewModel by viewModels({ requireParentFragment() })

    lateinit var state: InputState
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
                    etSignupNickname.setColorStateWithInput(
                        InputState.ON,
                        tvSignupNameRule,
                        signupEtCheck
                    )
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
                    state = when {
                        s != null && s.length !in NAME_RANGE -> {
                            pgSignupNameCheck.visibility = View.INVISIBLE
                            tvSignupNameRule.text = getString(R.string.singup_error_text)
                            InputState.ERROR
                        }

                        s.isNullOrEmpty() -> {
                            pgSignupNameCheck.visibility = View.INVISIBLE
                            tvSignupNameRule.text = getString(R.string.signup_name_rule)
                            InputState.ON
                        }

                        else -> {
                            pgSignupNameCheck.visibility = View.VISIBLE
                            InputState.ACCEPT
                        }
                    }

                    lifecycleScope.launch {
                        viewModel.setNameData("")
                        if(state == InputState.ACCEPT){
                            delay(1500)
                            val isValid = withContext(Dispatchers.IO) {
                                // 검증 로직
                                s!!.length == 5
                            }
                            tvSignupNameRule.text = getString(R.string.signup_check_text)

                            state = if (isValid) {
                                pgSignupNameCheck.visibility = View.INVISIBLE
                                tvSignupNameRule.text = getString(R.string.signup_confirm_text)
                                viewModel.setNameData(s.toString())
                                InputState.ACCEPT
                            } else {
                                pgSignupNameCheck.visibility = View.INVISIBLE
                                tvSignupNameRule.text = getString(R.string.signup_exists_text)
                                InputState.ERROR
                            }
                            etSignupNickname.setColorStateWithInput(
                                state,
                                tvSignupNameRule,
                                signupEtCheck
                            )
                        }else{
                            etSignupNickname.setColorStateWithInput(
                                state,
                                tvSignupNameRule,
                                signupEtCheck
                            )
                        }
                    }

                }
            })
        }
    }
}