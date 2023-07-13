package com.shypolarbear.presentation.ui.login

import android.content.Context
import android.text.util.Linkify
import android.text.util.Linkify.addLinks
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.shypolarbear.domain.model.login.LoginToken
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentLoginBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.regex.Pattern

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    R.layout.fragment_login
) {
    companion object{
        private const val LOGIN_SUCCESS = 200
        private const val LOGIN_FAIL = 403
        private const val SIGNUP_NEED = 404
    }

    override val viewModel: LoginViewModel by viewModels()

    private val linkify = Linkify()
    private val transformFilter = Linkify.TransformFilter { match, url -> "" }
    lateinit var loginToken: LoginToken
    lateinit var kakaoCallBack: (OAuthToken?, Throwable?) -> Unit
    override fun initView() {
        val terms = Pattern.compile(getString(R.string.terms))
        val privacyPolicy = Pattern.compile(getString(R.string.privacy_policy))
        var stateCodeLogIn = SIGNUP_NEED

        kakaoTokenCheck(requireContext())

        binding.btnLogin.setOnClickListener {
            // 로그인 구현할 때 UIState도입예정
            binding.btnClickedLogin.visibility = View.VISIBLE
            binding.progressLogin.visibility = View.VISIBLE
            binding.ivKakaotalk.visibility = View.INVISIBLE

            setKaKaoCallBack()
            lifecycleScope.launch {
                val job = async{
                    stateCodeLogIn = kakaoLogin(requireContext())
                }
                job.await()
                when(stateCodeLogIn){
                    LOGIN_FAIL ->{

                    }
                    LOGIN_SUCCESS ->{

                    }
                    SIGNUP_NEED ->{

                    }
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
    fun setKaKaoCallBack(){
        kakaoCallBack = { token, error ->
            if (error != null) {
                Timber.tag("KAKAO").e(error, getString(R.string.kakao_ac_login_fail))
            } else if (token != null) {
                Timber.tag("KAKAO").i(getString(R.string.kakao_ac_login_success))
            }
        }
    }
    private fun kakaoTokenCheck(context: Context){
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error != null) {
                    if (error is KakaoSdkError && error.isInvalidTokenError()) {
                        Toast.makeText(context, "로그인이 필요합니다",Toast.LENGTH_SHORT).show()
                    }
                    else {
                        // 기타 에러
                    }
                }
                else {
                    // 메인페이지로 이동
                }
            }
        }
        else {
            Toast.makeText(context, "로그인이 필요합니다",Toast.LENGTH_SHORT).show()
        }
    }
    private fun kakaoLogin(context: Context): Int{
        var stateCodeLogIn = SIGNUP_NEED

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Timber.tag("KAKAO").e(error, getString(R.string.kakao_fail))
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallBack)
                } else if (token != null) {
                    Timber.tag("KAKAO").i(getString(R.string.kakao_success))
                    loginToken = LoginToken(token.accessToken)
                    stateCodeLogIn = LOGIN_SUCCESS
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallBack)
        }
        return stateCodeLogIn
    }

    fun kakaoLogout(){
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Timber.tag("KAKAO").e(error, getString(R.string.kakao_fail))
            }else {
                Timber.tag("KAKAO").i(getString(R.string.kakao_success))
            }
        }
    }
    fun kakaoUnlink(){
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Timber.tag("KAKAO").e(error, getString(R.string.kakao_unlink_fail))
            }else {
                Timber.tag("KAKAO").i(getString(R.string.kakao_unlink_success))
            }
        }
    }
}