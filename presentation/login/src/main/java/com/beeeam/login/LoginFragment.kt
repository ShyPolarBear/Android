package com.beeeam.login

import android.content.Context
import android.text.util.Linkify
import android.text.util.Linkify.addLinks
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.beeeam.base.BaseFragment
import com.beeeam.login.databinding.FragmentLoginBinding
import com.beeeam.util.Const.LOGIN_FAIL
import com.beeeam.util.Const.LOGIN_SUCCESS
import com.beeeam.util.Const.SIGNUP_NEED
import com.beeeam.util.setVisibilityInvert
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.regex.Pattern

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    R.layout.fragment_login,
) {

    override val viewModel: LoginViewModel by viewModels()
    private val linkify = Linkify()
    private val transformFilter = Linkify.TransformFilter { match, url -> "" }
    private lateinit var kakaoCallBack: (OAuthToken?, Throwable?) -> Unit
    private lateinit var sendTokenToJoin: NavDirections

    override fun initView() {
        val terms = Pattern.compile(getString(com.beeeam.designsystem.R.string.terms))
        val privacyPolicy = Pattern.compile(getString(com.beeeam.designsystem.R.string.privacy_policy))
        val key = Utility.getKeyHash(requireContext())

        setKakaoCallBack()

        viewModel.responseCode.observe(viewLifecycleOwner) { code ->
            code?.let {
                when (it) {
                    SIGNUP_NEED -> findNavController().navigate(sendTokenToJoin)
                    LOGIN_SUCCESS -> findNavController().navigate(R.id.action_loginFragment_to_quizMainFragment)
                    LOGIN_FAIL -> {
                        setVisibilityInvert(
                            binding.btnClickedLogin,
                            binding.progressLogin,
                            binding.ivKakaotalk,
                        )
                        Toast.makeText(context, getString(com.beeeam.designsystem.R.string.login_fail), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.apply {
            btnLogin.setOnClickListener {
                setVisibilityInvert(btnClickedLogin, progressLogin, ivKakaotalk)
                kakaoLogin(requireContext())
            }

            linkify.apply {
                addLinks(
                    tvLoginTerms,
                    terms,
                    getString(com.beeeam.designsystem.R.string.terms_url),
                    null,
                    transformFilter,
                )
                addLinks(
                    tvLoginTerms,
                    privacyPolicy,
                    getString(com.beeeam.designsystem.R.string.privacy_url),
                    null,
                    transformFilter,
                )
            }
        }
    }

    private fun setKakaoCallBack() {
        kakaoCallBack = { token, error ->
            if (error != null) {
                Timber.tag("KAKAO").e(error, "카카오계정으로 로그인 실패")
                setVisibilityInvert(
                    binding.btnClickedLogin,
                    binding.progressLogin,
                    binding.ivKakaotalk,
                )
            } else if (token != null) {
                Timber.tag("KAKAO").i("카카오계정으로 로그인 성공")
                sendTokenToJoin =
                    LoginFragmentDirections.actionLoginFragmentToSignupFragment(token.accessToken)
                viewModel.requestLogin(token.accessToken)
            }
        }
    }

    private fun kakaoLogin(context: Context) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Timber.tag("KAKAO").e(error, "카카오톡 login 실패")
                    setVisibilityInvert(
                        binding.btnClickedLogin,
                        binding.progressLogin,
                        binding.ivKakaotalk,
                    )

                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallBack)
                } else if (token != null) {
                    Timber.tag("KAKAO").i("카카오톡 login 성공")
                    sendTokenToJoin =
                        LoginFragmentDirections.actionLoginFragmentToSignupFragment(token.accessToken)
                    viewModel.requestLogin(token.accessToken)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallBack)
        }
    }
}
