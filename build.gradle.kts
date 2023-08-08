buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://devrepo.kakao.com/nexus/content/groups/public/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.GRADLE}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN_VERSION}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.NAV_SAFE_ARGS_VERSION}")
    }
}

val clean by tasks.registering(Delete::class) {
    delete(rootProject.buildDir)
}