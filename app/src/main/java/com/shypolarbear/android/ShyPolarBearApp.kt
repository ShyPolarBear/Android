package com.shypolarbear.android

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.kakao.sdk.common.KakaoSdk
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
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

}