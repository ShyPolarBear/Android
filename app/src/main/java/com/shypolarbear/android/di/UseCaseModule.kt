package com.shypolarbear.android.di

import com.shypolarbear.domain.repository.ExampleRepo
import com.shypolarbear.domain.repository.feed.FeedRepo
import com.shypolarbear.domain.usecase.ExampleUseCase
import com.shypolarbear.domain.usecase.feed.FeedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideExampleUseCase(repo: ExampleRepo): ExampleUseCase {
        return ExampleUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideFeedTotalUseCase(repo: FeedRepo): FeedUseCase {
        return FeedUseCase(repo)
    }
}