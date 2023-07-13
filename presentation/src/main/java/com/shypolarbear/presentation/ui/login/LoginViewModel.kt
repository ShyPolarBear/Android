package com.shypolarbear.presentation.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.shypolarbear.domain.model.feed.FeedPost
import com.shypolarbear.domain.model.login.LoginToken
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseViewModel
import timber.log.Timber

class LoginViewModel: BaseViewModel() {
    lateinit var loginToken: LoginToken

    suspend fun kakaoLogin(context: Context){

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Timber.tag("KAKAO").e(error, context.getString(R.string.kakao_ac_login_fail))
            } else if (token != null) {
                Timber.tag("KAKAO").i(context.getString(R.string.kakao_ac_login_success))
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Timber.tag("KAKAO").e(error, context.getString(R.string.kakao_fail))
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Timber.tag("KAKAO").i(context.getString(R.string.kakao_success))
                    loginToken = LoginToken(token.accessToken, token.refreshToken, token.accessTokenExpiresAt, token.refreshTokenExpiresAt)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    fun kakaoLogout(context: Context){
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Timber.tag("KAKAO").e(error, context.getString(R.string.kakao_fail))
            }else {
                Timber.tag("KAKAO").i(context.getString(R.string.kakao_success))
            }
        }
    }
    fun kakaoUnlink(context: Context){
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Timber.tag("KAKAO").e(error, context.getString(R.string.kakao_unlink_fail))
            }else {
                Timber.tag("KAKAO").i(context.getString(R.string.kakao_unlink_success))
            }
        }
    }
}