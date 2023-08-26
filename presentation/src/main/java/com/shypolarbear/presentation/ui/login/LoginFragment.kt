package com.shypolarbear.presentation.ui.login

import android.content.Context
import android.text.util.Linkify
import android.text.util.Linkify.addLinks
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.shypolarbear.domain.model.login.LoginRequest
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.regex.Pattern

private const val SIGNUP_NEED = 1006
private const val LOGIN_SUCCESS = 0

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    R.layout.fragment_login
) {

    override val viewModel: LoginViewModel by viewModels()
    private val linkify = Linkify()
    private val transformFilter = Linkify.TransformFilter { match, url -> "" }
    lateinit var kakaoCallBack: (OAuthToken?, Throwable?) -> Unit

    override fun initView() {
        val terms = Pattern.compile(getString(R.string.terms))
        val privacyPolicy = Pattern.compile(getString(R.string.privacy_policy))
        val key = Utility.getKeyHash(requireContext())

        viewModel.responseCode.observe(viewLifecycleOwner) { code ->
            code?.let {
                Timber.tag("CODE").d("$code")
                when (it) {
                    SIGNUP_NEED -> findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
                    LOGIN_SUCCESS -> findNavController().navigate(R.id.action_loginFragment_to_quizMainFragment)
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            // 로그인 구현할 때 UIState도입예정
            binding.btnClickedLogin.visibility = View.VISIBLE
            binding.progressLogin.visibility = View.VISIBLE
            binding.ivKakaotalk.visibility = View.INVISIBLE
            setKakaoCallBack()
            kakaoLogin(requireContext())

            binding.btnClickedLogin.visibility = View.INVISIBLE
            binding.progressLogin.visibility = View.INVISIBLE
            binding.ivKakaotalk.visibility = View.VISIBLE
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

    private fun setKakaoCallBack() {
        kakaoCallBack = { token, error ->
            if (error != null) {
                Timber.tag("KAKAO").e(error, "카카오계정으로 로그인 실패")
            } else if (token != null) {
                Timber.tag("KAKAO").i("카카오계정으로 로그인 성공")
                viewModel.postLogin(token.accessToken)
            }
        }
    }

    private fun kakaoLogin(context: Context) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Timber.tag("KAKAO").e(error, "카카오톡 login 실패")
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallBack)
                } else if (token != null) {
                    Timber.tag("KAKAO").i("카카오톡 login 성공")
                    viewModel.postLogin(token.accessToken)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallBack)
        }
    }

    fun kakaoLogout() {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Timber.tag("KAKAO").e(error, "카카오톡 logout 실패")
            } else {
                Timber.tag("KAKAO").i("카카오톡 logout 성공")
            }
        }
    }

    fun kakaoUnlink() {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Timber.tag("KAKAO").e(error, "카카오톡 unlink 실패")
            } else {
                Timber.tag("KAKAO").i("카카오톡 unlink 성공")
            }
        }
    }
}