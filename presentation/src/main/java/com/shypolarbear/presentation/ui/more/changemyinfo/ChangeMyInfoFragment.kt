package com.shypolarbear.presentation.ui.more.changemyinfo

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
import com.shypolarbear.presentation.util.InputState
import com.shypolarbear.presentation.util.keyboardDown
import com.shypolarbear.presentation.util.setColorStateWithInput

class ChangeMyInfoFragment: BaseFragment<FragmentChangeMyInfoBinding, ChangeMyInfoViewModel> (
    R.layout.fragment_change_my_info
) {

    override val viewModel: ChangeMyInfoViewModel by viewModels()

    override fun initView() {
        val bottomNavigationViewMainActivity = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)
        bottomNavigationViewMainActivity.isVisible = false

        binding.apply {
            btnChangeMyInfoBack.setOnClickListener {
                findNavController().navigate(R.id.action_changeMyInfoFragment_to_navigation_more)
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
        }
    }
}