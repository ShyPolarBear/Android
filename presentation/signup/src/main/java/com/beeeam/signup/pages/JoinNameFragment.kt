package com.beeeam.signup.pages

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.beeeam.base.BaseFragment
import com.beeeam.signup.JoinViewModel
import com.beeeam.signup.R
import com.beeeam.signup.databinding.FragmentSignupNameBinding
import com.beeeam.util.Const.NAME_RANGE
import com.beeeam.util.GlideUtil
import com.beeeam.util.ImageUtil
import com.beeeam.util.InputState
import com.beeeam.util.keyboardDown
import com.beeeam.util.setColorStateWithInput
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinNameFragment :
    BaseFragment<FragmentSignupNameBinding, JoinViewModel>(R.layout.fragment_signup_name) {
    override val viewModel: JoinViewModel by viewModels({ requireParentFragment() })

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let { profileImage ->
                GlideUtil.loadCircleImage(requireContext(), uri, binding.ivSignupNameProfile)
                viewModel.requestImageUploadWithJoin(listOf(ImageUtil.uriToOptimizeImageFile(requireContext(), profileImage)!!))
            }
        }

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {
        binding.apply {
            ivSignupImgEdit.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            etSignupNickname.apply {
                keyboardDown(this@JoinNameFragment)
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
                            signupEtCheck,
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
                            signupEtCheck,
                        )
                    }

                    override fun afterTextChanged(s: Editable?) {
                        val state = when {
                            s.isNullOrEmpty() -> {
                                tvSignupNameRule.text = getString(com.beeeam.designsystem.R.string.signup_name_rule)
                                InputState.ON
                            }

                            s.length !in NAME_RANGE -> {
                                tvSignupNameRule.text = getString(com.beeeam.designsystem.R.string.singup_error_text)
                                InputState.ERROR
                            }

                            else -> {
                                tvSignupNameRule.text = getString(com.beeeam.designsystem.R.string.signup_confirm_text)
                                InputState.ACCEPT
                            }
                        }

                        setColorStateWithInput(
                            state,
                            tvSignupNameRule,
                            signupEtCheck,
                        )
                        viewModel.setNameData(s.toString())
                    }
                })
            }
        }
    }
}
