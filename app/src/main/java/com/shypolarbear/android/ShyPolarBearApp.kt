package com.shypolarbear.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ShyPolarBearApp: Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }
    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

}