package com.shypolarbear.android.di

import com.shypolarbear.data.api.ExampleApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

// NetworkModule로 retrofit 객체를 생성하고 여기에서 각 Api에 맞게 create 하여 사용
// 이런 형식으로 사용할 예정

    @Singleton
    @Provides
    fun provideExampleApi(retrofit: Retrofit): ExampleApi {
        return retrofit.create(ExampleApi::class.java)
    }
}