package com.shypolarbear.presentation.ui.login

import android.text.util.Linkify
import android.text.util.Linkify.addLinks
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.common.util.Utility
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentLoginBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        val keyHash = Utility.getKeyHash(requireContext())
        Timber.d("HASH_KEY: $keyHash")

        binding.btnLogin.setOnClickListener {
            // 로그인 구현할 때 UIState도입예정
            binding.btnClickedLogin.visibility = View.VISIBLE
            binding.progressLogin.visibility = View.VISIBLE
            binding.ivKakaotalk.visibility = View.INVISIBLE

            lifecycleScope.launch {
                var stateCodeLogIn = 404
                val job = async{
                    // 로그인 로직이 들어갈 곳
                    delay(2000)
                    //stateCodeLogIn = 200
                }
                job.await()
                if(stateCodeLogIn == 404){

                }
                binding.btnClickedLogin.visibility = View.INVISIBLE
                binding.progressLogin.visibility = View.INVISIBLE
                binding.ivKakaotalk.visibility = View.VISIBLE
            }
        }



        linkify.apply {
            addLinks(
                binding.tvLoginTerms,
                terms,
                getString(R.string.terms_url),
                null,
                transformFilter
            )
            addLinks(
                binding.tvLoginTerms,
                privacyPolicy,
                getString(R.string.privacy_url),
                null,
                transformFilter
            )
        }
    }
}