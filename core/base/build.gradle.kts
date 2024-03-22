plugins {
    id("com.android.library")
    id("kotlin-android")
    id("androidx.navigation.safeargs.kotlin")

    kotlin("android")
}

android {
    namespace = "com.beeeam.base"
    compileSdk = Configuration.COMPILE_SDK

    defaultConfig {
        minSdk = Configuration.MIN_SDK
        targetSdk = Configuration.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(project(":core:util"))

    implementation(AndroidX.CORE_KTX)
    implementation(AndroidX.APP_COMPAT)
    implementation(Google.MATERIAL)
    implementation(AndroidX.FRAGMENT_KTX)
    implementation(AndroidX.LIFECYCLE_VIEWMODEL_KTX)
    testImplementation(AndroidX.JUNIT)
    androidTestImplementation(AndroidX.EXT_JUNIT)
    androidTestImplementation(AndroidX.ESPRESSO_CORE)
}