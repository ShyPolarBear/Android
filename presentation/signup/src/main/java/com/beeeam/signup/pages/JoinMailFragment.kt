package com.beeeam.signup.pages

import androidx.fragment.app.viewModels
import com.beeeam.base.BaseFragment
import com.beeeam.signup.JoinViewModel
import com.beeeam.signup.R
import com.beeeam.signup.databinding.FragmentSignupMailBinding
import com.beeeam.util.Const.emailPattern
import com.beeeam.util.InputState
import com.beeeam.util.afterTextChanged
import com.beeeam.util.keyboardDown
import com.beeeam.util.setColorStateWithInput
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
                            tvSignupMailRule.text = getString(com.beeeam.designsystem.R.string.signup_mail_hint_error)
                            InputState.ERROR
                        }

                        else -> {
                            tvSignupMailRule.text = getString(com.beeeam.designsystem.R.string.signup_mail_hint_confirm)
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
