package com.shypolarbear.android.di

import com.shypolarbear.data.api.ExampleApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    const val BASE_URL = "http://3.37.80.247:8080/"
    const val SAMPLE_URL = "https://api.chucknorris.io/"
    const val MOCK_URL = "https://ed3bf92e-bc49-483f-85b9-c042456779e9.mock.pstmn.io"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(MOCK_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}