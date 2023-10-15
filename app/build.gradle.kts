import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    // firebase
    id("com.google.gms.google-services")
    // Hilt
    id("dagger.hilt.android.plugin")

    kotlin("android")
    kotlin("kapt")
}

fun getApiKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}

android {
    namespace = "com.shypolarbear.android"
    compileSdk = Configuration.COMPILE_SDK

    defaultConfig {
        applicationId = "com.shypolarbear.android"
        minSdk = Configuration.MIN_SDK
        targetSdk = Configuration.TARGET_SDK
        versionCode = Configuration.VERSION_CODE
        versionName = Configuration.VERSION_NAME

        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", "\"${getApiKey("KAKAO_NATIVE_APP_KEY")}\"")
        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = getApiKey("KAKAO_NATIVE_APP_KEY")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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

    implementation(project(":presentation"))
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(AndroidX.APP_COMPAT)
    implementation(AndroidX.CORE_KTX)
    implementation(AndroidX.LIFECYCLE_RUNTIME)
    implementation(Google.MATERIAL)
    androidTestImplementation(AndroidX.EXT_JUNIT)
    androidTestImplementation(AndroidX.ESPRESSO_CORE)
    testImplementation(AndroidX.JUNIT)
    implementation(AndroidX.PREFERENCES_DATASTORE)
    implementation(AndroidX.PROTO_DATASTORE)

    implementation(Google.HILT_ANDROID)
    kapt(Google.HILT_ANDROID_COMPILER)

    implementation(SquareUp.RETROFIT2)
    implementation(SquareUp.RETROFIT2_CONVERTER_GSON)
    implementation(SquareUp.OKHTTP3)
    implementation(SquareUp.OKHTTP3_LOGGING)
    implementation(SquareUp.OKHTTP3_BOM)

    implementation(Jakewharton.TIMBER)
    implementation(Kakao.KAKAO)
    implementation(platform(Firebase.FIREBASE))
}