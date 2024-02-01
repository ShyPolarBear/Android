package com.shypolarbear.android.di

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.shypolarbear.android.util.BASE_URL
import com.shypolarbear.android.util.RETROFIT_TAG
import com.shypolarbear.android.util.isJsonArray
import com.shypolarbear.android.util.isJsonObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    @NormalOkHttp
    fun provideHttpClient(
        logger: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(logger)
            .build()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor {
            when {
                !it.isJsonArray() && !it.isJsonObject() ->
                    Timber.tag(RETROFIT_TAG).d("CONNECTION INFO: $it")
                else -> try {
                    Timber.tag(RETROFIT_TAG).d(
                        GsonBuilder().setPrettyPrinting().create().toJson(
                            JsonParser().parse(it),
                        ),
                    )
                } catch (m: JsonSyntaxException) {
                    Timber.tag(RETROFIT_TAG).d(it)
                }
            }
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Singleton
    @Provides
    @NormalRetrofit
    fun provideRetrofit(
        @NormalOkHttp client: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}
