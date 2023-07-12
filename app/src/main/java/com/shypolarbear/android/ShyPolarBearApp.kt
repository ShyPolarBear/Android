package com.shypolarbear.android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ShyPolarBearApp: Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }
    private fun initKakaoLogin(){
        KakaoSdk.init(this, "358c8f5c265462709f95a2177bef2fd6")
    }
    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

}