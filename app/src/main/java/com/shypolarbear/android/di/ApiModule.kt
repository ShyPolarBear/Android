package com.shypolarbear.android.di

import com.shypolarbear.data.api.ExampleApi
import com.shypolarbear.data.api.LoginApi
import com.shypolarbear.data.api.feed.FeedApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

// NetworkModule로 retrofit 객체를 생성하고 여기에서 각 Api에 맞게 create 하여 사용
// 이런 형식으로 사용할 예정
    @Singleton
    @Provides
    fun provideLoginApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Singleton
    @Provides
    fun provideExampleApi(@NormalRetrofit retrofit: Retrofit): ExampleApi {
        return retrofit.create(ExampleApi::class.java)
    }

    @Singleton
    @Provides
    fun provideFeedApi(@AuthRetrofit retrofit: Retrofit): FeedApi {
        return retrofit.create(FeedApi::class.java)
    }
}