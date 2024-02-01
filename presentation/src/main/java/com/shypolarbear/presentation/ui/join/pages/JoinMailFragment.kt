package com.shypolarbear.presentation.ui.join.pages

import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupMailBinding
import com.shypolarbear.presentation.ui.join.JoinViewModel
import com.shypolarbear.presentation.util.InputState
import com.shypolarbear.presentation.util.afterTextChanged
import com.shypolarbear.presentation.util.emailPattern
import com.shypolarbear.presentation.util.keyboardDown
import com.shypolarbear.presentation.util.setColorStateWithInput
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinMailFragment :
    BaseFragment<FragmentSignupMailBinding, JoinViewModel>(R.layout.fragment_signup_mail) {
    override val viewModel: JoinViewModel by viewModels({ requireParentFragment() })
    override fun initView() {
        binding.apply {
            etSignupMail.apply {
                keyboardDown(this@JoinMailFragment)
                afterTextChanged {
                    val match: MatchResult? = emailPattern.find(it.toString())

                    val state = when {
                        it.isNullOrEmpty() -> {
                            InputState.ON
                        }

                        match == null -> {
                            tvSignupMailRule.text = getString(R.string.signup_mail_hint_error)
                            InputState.ERROR
                        }

                        else -> {
                            tvSignupMailRule.text = getString(R.string.signup_mail_hint_confirm)
                            InputState.ACCEPT
                        }
                    }
                    setColorStateWithInput(
                        state,
                        tvSignupMailRule,
                        signupEtCheck,
                    )
                    viewModel.setMailData(match?.value ?: "")
                }
            }
        }
    }
}
