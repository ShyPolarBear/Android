package com.shypolarbear.presentation.ui.more.changemyinfo

import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
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
import timber.log.Timber

class ChangeMyInfoFragment: BaseFragment<FragmentChangeMyInfoBinding, ChangeMyInfoViewModel> (
    R.layout.fragment_change_my_info
) {

    override val viewModel: ChangeMyInfoViewModel by viewModels()
    private lateinit var phoneNumber: String
    private var nameState: InputState = InputState.OFF
    private var phoneNumberState: InputState = InputState.OFF
    private var emailState: InputState = InputState.OFF

    override fun initView() {
        val bottomNavigationViewMainActivity = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)
        bottomNavigationViewMainActivity.isVisible = false

        binding.apply {
            btnChangeMyInfoBack.setOnClickListener {
                findNavController().navigate(R.id.action_changeMyInfoFragment_to_navigation_more)
            }

            btnChangeMyInfoRevise.setOnClickListener {

                if (nameState == InputState.ERROR || phoneNumberState == InputState.ERROR || emailState == InputState.ERROR) {
                    Toast.makeText(requireContext(), getString(R.string.check_my_info_term), Toast.LENGTH_SHORT).show()
                }
                else if (nameState == InputState.OFF && phoneNumberState == InputState.OFF && emailState == InputState.OFF) {
                    Toast.makeText(requireContext(), getString(R.string.check_my_info_input), Toast.LENGTH_SHORT).show()
                }
                else {
                    // TODO("수정 된 정보 서버로 전달")
                    findNavController().navigate(R.id.action_changeMyInfoFragment_to_navigation_more)
                }

            }

            edtChangeMyInfoNickname.apply {
                keyboardDown(this@ChangeMyInfoFragment)
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int,
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
                        count: Int,
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
                                nameState = InputState.ON
                                InputState.ON
                            }

                            s.length !in NAME_RANGE -> {
                                tvChangeMyInfoNameRule.text = getString(R.string.singup_error_text)
                                nameState = InputState.ERROR
                                InputState.ERROR
                            }

                            else -> {
                                tvChangeMyInfoNameRule.text = getString(R.string.signup_confirm_text)
                                nameState = InputState.ACCEPT
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
                            phoneNumberState = InputState.ON
                            InputState.ON
                        }

                        s.length == PHONE_NUMBER_DASH_INCLUDE -> {
                            phoneNumber = s.replace(phonePattern, "")
                            tvChangeMyInfoPhoneNumberRule.text = getString(R.string.signup_phone_hint_confirm)
                            phoneNumberState = InputState.ACCEPT
                            InputState.ACCEPT
                        }

                        else -> {
                            phoneNumber = ""
                            tvChangeMyInfoPhoneNumberRule.text = getString(R.string.signup_phone_hint_error)
                            phoneNumberState = InputState.ERROR
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
                            emailState = InputState.ON
                            InputState.ON
                        }

                        match == null -> {
                            tvChangeMyInfoEmailRule.text = getString(R.string.signup_mail_hint_error)
                            emailState = InputState.ERROR
                            InputState.ERROR
                        }

                        else -> {
                            tvChangeMyInfoEmailRule.text = getString(R.string.signup_mail_hint_confirm)
                            emailState = InputState.ACCEPT
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