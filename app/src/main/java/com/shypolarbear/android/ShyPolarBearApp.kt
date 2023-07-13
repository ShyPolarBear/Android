package com.shypolarbear.android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.v2.all.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ShyPolarBearApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKakaoLogin()
        setupTimber()
    }
    private fun initKakaoLogin(){
        KakaoSdk.init(this, getString(com.shypolarbear.presentation.R.string.kakao_native_app_key))
    }
    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

}