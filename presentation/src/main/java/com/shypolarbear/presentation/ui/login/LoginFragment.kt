package com.shypolarbear.presentation.ui.login

import android.text.util.Linkify
import android.text.util.Linkify.addLinks
import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentLoginBinding
import timber.log.Timber
import java.util.regex.Pattern

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    R.layout.fragment_login
) {
    override val viewModel: LoginViewModel by viewModels()

    private val linkify = Linkify()
    private val transformFilter = Linkify.TransformFilter { match, url -> "" }

    override fun initView() {
        val terms = Pattern.compile(getString(R.string.terms))
        val privacyPolicy = Pattern.compile(getString(R.string.privacy_policy))

        binding.btnLogin.setOnClickListener {

        }

        linkify.apply {
            addLinks(
                binding.tvLoginTerms,
                terms,
                getString(R.string.TERMS_URL),
                null,
                transformFilter
            )
            addLinks(
                binding.tvLoginTerms,
                privacyPolicy,
                getString(R.string.PRIVACY_URL),
                null,
                transformFilter
            )
        }
    }
}