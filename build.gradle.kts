// Top-level build file where you can add configuration options common to all sub-projects/modules.
//plugins {
//    id ("com.android.application") version '8.0.1' apply false
//    id ("com.android.library") version '8.0.1' apply false
//    id ("org.jetbrains.kotlin.android") version '1.7.20' apply false
//    id ("org.jetbrains.kotlin.jvm") version '1.8.20' apply false
//}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.GRADLE}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN_VERSION}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}")
    }
}

val clean by tasks.registering(Delete::class) {
    delete(rootProject.buildDir)
}