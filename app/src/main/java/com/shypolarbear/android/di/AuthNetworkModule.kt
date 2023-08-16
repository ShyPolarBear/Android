package com.shypolarbear.android.di

import com.shypolarbear.android.util.MOCK_URL
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
object AuthNetworkModule {

    @Singleton
    @Provides
    @AuthOkHttp
    fun provideAuthHttpClient(
        logger: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(authInterceptor)
            .build()
    }
    @Singleton
    @Provides
    @AuthRetrofit
    fun provideAuthRetrofit(
        @AuthOkHttp client: OkHttpClient
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(MOCK_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}