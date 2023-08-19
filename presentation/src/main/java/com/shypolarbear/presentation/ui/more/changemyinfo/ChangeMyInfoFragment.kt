package com.shypolarbear.presentation.ui.more.changemyinfo

import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentChangeMyInfoBinding
import com.shypolarbear.presentation.ui.signup.NAME_RANGE
import com.shypolarbear.presentation.ui.signup.pages.PHONE_NUMBER_DASH_INCLUDE
import com.shypolarbear.presentation.util.InputState
import com.shypolarbear.presentation.util.afterTextChanged
import com.shypolarbear.presentation.util.emailPattern
import com.shypolarbear.presentation.util.keyboardDown
import com.shypolarbear.presentation.util.phonePattern
import com.shypolarbear.presentation.util.setColorStateWithInput

class ChangeMyInfoFragment: BaseFragment<FragmentChangeMyInfoBinding, ChangeMyInfoViewModel> (
    R.layout.fragment_change_my_info
) {

    override val viewModel: ChangeMyInfoViewModel by viewModels()
    private lateinit var phoneNumber: String

    override fun initView() {
        val bottomNavigationViewMainActivity = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)
        bottomNavigationViewMainActivity.isVisible = false

        binding.apply {
            btnChangeMyInfoBack.setOnClickListener {
                findNavController().navigate(R.id.action_changeMyInfoFragment_to_navigation_more)
            }

            btnChangeMyInfoRevise.setOnClickListener {
                // TODO("내 정보 수정 조건 검사 완료 후 이전 페이지로 이동")
                findNavController().navigate(R.id.action_changeMyInfoFragment_to_navigation_more)
            }

            edtChangeMyInfoNickname.apply {
                keyboardDown(this@ChangeMyInfoFragment)
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        setColorStateWithInput(
                            InputState.ON,
                            tvChangeMyInfoNameRule,
                            ivChangeMyInfoNameCheck
                        )
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        setColorStateWithInput(
                            InputState.ON,
                            tvChangeMyInfoNameRule,
                            ivChangeMyInfoNameCheck
                        )
                    }

                    override fun afterTextChanged(s: Editable?) {
                        val state = when {
                            s.isNullOrEmpty() -> {
                                tvChangeMyInfoNameRule.text = getString(R.string.signup_name_rule)
                                InputState.ON
                            }

                            s.length !in NAME_RANGE -> {
                                tvChangeMyInfoNameRule.text = getString(R.string.singup_error_text)
                                InputState.ERROR
                            }

                            else -> {
                                tvChangeMyInfoNameRule.text = getString(R.string.signup_confirm_text)
                                InputState.ACCEPT
                            }
                        }

                        setColorStateWithInput(
                            state,
                            tvChangeMyInfoNameRule,
                            ivChangeMyInfoNameCheck
                        )
                    }
                })
            }

            edtChangeMyInfoPhoneNumber.apply {
                keyboardDown(this@ChangeMyInfoFragment)
                addTextChangedListener(PhoneNumberFormattingTextWatcher("KR"))
                afterTextChanged { s ->
                    val state = when {
                        s.isNullOrEmpty() -> {
                            tvChangeMyInfoPhoneNumberRule.text = getString(R.string.signup_phone_hint_detail)
                            InputState.ON
                        }

                        s.length == PHONE_NUMBER_DASH_INCLUDE -> {
                            phoneNumber = s.replace(phonePattern, "")
                            tvChangeMyInfoPhoneNumberRule.text = getString(R.string.signup_phone_hint_confirm)
                            InputState.ACCEPT
                        }

                        else -> {
                            phoneNumber = ""
                            tvChangeMyInfoPhoneNumberRule.text = getString(R.string.signup_phone_hint_error)
                            InputState.ERROR
                        }
                    }
                    setColorStateWithInput(
                        state,
                        tvChangeMyInfoPhoneNumberRule,
                        ivChangeMyInfoPhoneNumberCheck
                    )
                }
            }

            edtChangeMyInfoEmail.apply {
                keyboardDown(this@ChangeMyInfoFragment)
                afterTextChanged {
                    val match: MatchResult? = emailPattern.find(it.toString())

                    val state = when {
                        it.isNullOrEmpty() -> {
                            InputState.ON
                        }

                        match == null -> {
                            tvChangeMyInfoEmailRule.text = getString(R.string.signup_mail_hint_error)
                            InputState.ERROR
                        }

                        else -> {
                            tvChangeMyInfoEmailRule.text = getString(R.string.signup_mail_hint_confirm)
                            InputState.ACCEPT
                        }
                    }
                    setColorStateWithInput(
                        state,
                        tvChangeMyInfoEmailRule,
                        ivChangeMyInfoEmailCheck
                    )
                }
            }
        }
    }
}