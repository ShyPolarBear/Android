package com.shypolarbear.presentation.ui.signup.pages

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupNameBinding
import com.shypolarbear.presentation.ui.signup.NAME_RANGE
import com.shypolarbear.presentation.ui.signup.SignupViewModel
import com.shypolarbear.presentation.util.GlideUtil
import com.shypolarbear.presentation.util.hideKeyboard
import com.shypolarbear.presentation.util.keyboardDown
import com.shypolarbear.presentation.util.setTextColorById
import timber.log.Timber

class SignupNameFragment :
    BaseFragment<FragmentSignupNameBinding, SignupViewModel>(R.layout.fragment_signup_name) {
    override val viewModel: SignupViewModel by viewModels({ requireParentFragment() })

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {

        binding.apply {

            val pickMedia =
                registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                    if (uri != null) {
                        GlideUtil.loadCircleImage(requireContext(), uri, ivSignupNameProfile)
                    } else {
                        Timber.d("이미지 선택 안됨")
                    }
                }

            ivSignupImgEdit.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            etSignupNickname.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    tvSignupNameRule.setTextColorById(requireContext(), R.color.Blue_02)
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
                }

                override fun afterTextChanged(s: Editable?) {
                    when {
                        s != null && s.length !in NAME_RANGE -> {
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
        // 프로필 이미지를 uri나 bitmap/ mime Type으로 보내는 것은 추후 설정
        viewModel.setNameData(data)
    }
}