plugins {

    id("com.android.library")
    id("dagger.hilt.android.plugin")

    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.shypolarbear.presentation"
    compileSdk = Configuration.COMPILE_SDK

    defaultConfig {
        minSdk = Configuration.MIN_SDK
        targetSdk = Configuration.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles "consumer-rules.pro"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    dataBinding {
        enable = true
    }
}

dependencies {

    // Multi Module
    implementation(project(":domain"))

    implementation(AndroidX.FRAGMENT_KTX)
    implementation(AndroidX.LIFECYCLE_VIEWMODEL_KTX)
    implementation(AndroidX.CORE_KTX)
    implementation(AndroidX.APP_COMPAT)
    implementation(Google.MATERIAL)
    testImplementation(AndroidX.JUNIT)
    androidTestImplementation(AndroidX.EXT_JUNIT)
    androidTestImplementation(AndroidX.ESPRESSO_CORE)

    // Hilt
    implementation(Google.HILT_ANDROID)
    kapt(Google.HILT_ANDROID_COMPILER)

    // Coroutine
    implementation(KotlinX.KOTLINX_COROUTINE)

}