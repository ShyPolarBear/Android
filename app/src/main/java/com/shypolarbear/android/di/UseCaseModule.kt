package com.shypolarbear.android.di

import com.shypolarbear.domain.repository.ExampleRepo
import com.shypolarbear.domain.repository.InfoRepo
import com.shypolarbear.domain.repository.JoinRepo
import com.shypolarbear.domain.repository.feed.FeedRepo
import com.shypolarbear.domain.repository.LoginRepo
import com.shypolarbear.domain.repository.TokenRepo
import com.shypolarbear.domain.repository.quiz.QuizRepo
import com.shypolarbear.domain.usecase.tokens.GetAccessTokenUseCase
import com.shypolarbear.domain.usecase.ExampleUseCase
import com.shypolarbear.domain.usecase.more.GetMyInfoUseCase
import com.shypolarbear.domain.usecase.JoinUseCase
import com.shypolarbear.domain.usecase.feed.FeedTotalUseCase
import com.shypolarbear.domain.usecase.LoginUseCase
import com.shypolarbear.domain.usecase.tokens.GetRefreshTokenUseCase
import com.shypolarbear.domain.usecase.TokenRenewUseCase
import com.shypolarbear.domain.usecase.feed.FeedCommentUseCase
import com.shypolarbear.domain.usecase.feed.FeedDetailUseCase
import com.shypolarbear.domain.usecase.more.ChangeMyInfoUseCase
import com.shypolarbear.domain.usecase.quiz.QuizUseCase
import com.shypolarbear.domain.usecase.tokens.SetAccessTokenUseCase
import com.shypolarbear.domain.usecase.tokens.SetRefreshTokenUseCase
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
    fun provideJoinUseCase(repo: JoinRepo): JoinUseCase{
        return JoinUseCase(repo)
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
    fun provideGetAccessTokenUseCase(repo: TokenRepo): GetAccessTokenUseCase {
        return GetAccessTokenUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideSetAccessTokenUseCase(repo: TokenRepo): SetAccessTokenUseCase {
        return SetAccessTokenUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideSetRefreshTokenUseCase(repo: TokenRepo): SetRefreshTokenUseCase {
        return SetRefreshTokenUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideGetRefreshTokenUseCase(repo: TokenRepo): GetRefreshTokenUseCase {
        return GetRefreshTokenUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideTokenRenewUseCase(repo: TokenRepo): TokenRenewUseCase {
        return TokenRenewUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideGetMyInfoUseCase(repo: InfoRepo): GetMyInfoUseCase {
        return GetMyInfoUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideChangeMyInfoUseCase(repo: InfoRepo): ChangeMyInfoUseCase {
        return ChangeMyInfoUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideQuizUseCase(repo: QuizRepo): QuizUseCase{
        return QuizUseCase(repo)
    }
}