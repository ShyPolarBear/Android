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
        manifestPlaceholders["NATIVE_APP_KEY"] = "@string/kakao_native_app_key"
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
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(project(":domain"))

    implementation(AndroidX.CORE_KTX)
    implementation(AndroidX.APP_COMPAT)
    implementation(Google.MATERIAL)
    implementation(AndroidX.FRAGMENT_KTX)
    implementation(AndroidX.LIFECYCLE_VIEWMODEL_KTX)
    testImplementation(AndroidX.JUNIT)
    androidTestImplementation(AndroidX.EXT_JUNIT)
    androidTestImplementation(AndroidX.ESPRESSO_CORE)

    implementation(Google.HILT_ANDROID)
    kapt(Google.HILT_ANDROID_COMPILER)

    implementation(KotlinX.KOTLINX_COROUTINE)

    implementation(Jakewharton.TIMBER)
    implementation(AndroidX.SPLASH_SCREEN)
    implementation(PowerMenu.POWER_MENU)
    implementation(Glide.GLIDE)
    implementation(Kakao.KAKAO)

}