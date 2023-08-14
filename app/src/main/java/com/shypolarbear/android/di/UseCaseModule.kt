package com.shypolarbear.android.di

import com.shypolarbear.domain.repository.ExampleRepo
import com.shypolarbear.domain.repository.feed.FeedRepo
import com.shypolarbear.domain.repository.LoginRepo
import com.shypolarbear.domain.repository.TokenRepo
import com.shypolarbear.domain.usecase.AccessTokenUseCase
import com.shypolarbear.domain.usecase.ExampleUseCase
import com.shypolarbear.domain.usecase.feed.FeedTotalUseCase
import com.shypolarbear.domain.usecase.LoginUseCase
import com.shypolarbear.domain.usecase.RefreshTokenUseCase
import com.shypolarbear.domain.usecase.TokenRenewUseCase
import com.shypolarbear.domain.usecase.feed.FeedCommentUseCase
import com.shypolarbear.domain.usecase.feed.FeedDetailUseCase
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
    fun provideLoginUseCase(repo: LoginRepo): LoginUseCase{
        return LoginUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideExampleUseCase(repo: ExampleRepo): ExampleUseCase {
        return ExampleUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideFeedTotalUseCase(repo: FeedRepo): FeedTotalUseCase {
        return FeedTotalUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideFeedDetailUseCase(repo: FeedRepo): FeedDetailUseCase {
        return FeedDetailUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideFeedCommentUseCase(repo: FeedRepo): FeedCommentUseCase {
        return FeedCommentUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideAccessTokenUseCase(repo: TokenRepo): AccessTokenUseCase {
        return AccessTokenUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideRefreshTokenUseCase(repo: TokenRepo): RefreshTokenUseCase {
        return RefreshTokenUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideTokenRenewUseCase(repo: TokenRepo): TokenRenewUseCase {
        return TokenRenewUseCase(repo)
    }
}