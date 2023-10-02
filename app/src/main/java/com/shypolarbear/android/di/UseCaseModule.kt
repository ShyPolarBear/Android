package com.shypolarbear.android.di

import com.shypolarbear.domain.repository.ExampleRepo
import com.shypolarbear.domain.repository.image.ImageEditRepo
import com.shypolarbear.domain.repository.InfoRepo
import com.shypolarbear.domain.repository.JoinRepo
import com.shypolarbear.domain.repository.feed.FeedRepo
import com.shypolarbear.domain.repository.LoginRepo
import com.shypolarbear.domain.repository.TokenRepo
import com.shypolarbear.domain.repository.image.ImageUploadRepo
import com.shypolarbear.domain.repository.mypage.MyPostRepo
import com.shypolarbear.domain.repository.quiz.QuizRepo
import com.shypolarbear.domain.usecase.tokens.GetAccessTokenUseCase
import com.shypolarbear.domain.usecase.ExampleUseCase
import com.shypolarbear.domain.usecase.RequestJoinUseCase
import com.shypolarbear.domain.usecase.feed.LoadFeedTotalUseCase
import com.shypolarbear.domain.usecase.RequestLoginUseCase
import com.shypolarbear.domain.usecase.tokens.GetRefreshTokenUseCase
import com.shypolarbear.domain.usecase.RequestTokenRenewUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedChangeUseCase
import com.shypolarbear.domain.usecase.feed.LoadCommentUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedDeleteUseCase
import com.shypolarbear.domain.usecase.feed.LoadFeedDetailUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedCommentDeleteUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedCommentLikeUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedCommentWriteUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedLikeUseCase
import com.shypolarbear.domain.usecase.feed.RequestFeedWriteUseCase
import com.shypolarbear.domain.usecase.image.RequestImageDeleteUseCase
import com.shypolarbear.domain.usecase.image.RequestImageModifyUseCase
import com.shypolarbear.domain.usecase.image.RequestImageUploadUseCase
import com.shypolarbear.domain.usecase.more.RequestMyInfoChangeUseCase
import com.shypolarbear.domain.usecase.more.LoadMyInfoUseCase
import com.shypolarbear.domain.usecase.mypage.LoadMyPostUseCase
import com.shypolarbear.domain.usecase.quiz.QuizReviewUseCase
import com.shypolarbear.domain.usecase.quiz.QuizSolvedUseCase
import com.shypolarbear.domain.usecase.quiz.QuizSubmitMultiUseCase
import com.shypolarbear.domain.usecase.quiz.QuizSubmitOXUseCase
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
    fun provideLoginUseCase(repo: LoginRepo): RequestLoginUseCase {
        return RequestLoginUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideJoinUseCase(repo: JoinRepo): RequestJoinUseCase {
        return RequestJoinUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideExampleUseCase(repo: ExampleRepo): ExampleUseCase {
        return ExampleUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideFeedTotalUseCase(repo: FeedRepo): LoadFeedTotalUseCase {
        return LoadFeedTotalUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideFeedDetailUseCase(repo: FeedRepo): LoadFeedDetailUseCase {
        return LoadFeedDetailUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideFeedCommentUseCase(repo: FeedRepo): LoadCommentUseCase {
        return LoadCommentUseCase(repo)
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
    fun provideTokenRenewUseCase(repo: TokenRepo): RequestTokenRenewUseCase {
        return RequestTokenRenewUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideGetMyInfoUseCase(repo: InfoRepo): LoadMyInfoUseCase {
        return LoadMyInfoUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideChangeMyInfoUseCase(repo: InfoRepo): RequestMyInfoChangeUseCase {
        return RequestMyInfoChangeUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideQuizUseCase(repo: QuizRepo): QuizUseCase {
        return QuizUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideChangePostUseCase(repo: FeedRepo): RequestFeedChangeUseCase {
        return RequestFeedChangeUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideFeedDeleteUseCase(repo: FeedRepo): RequestFeedDeleteUseCase {
        return RequestFeedDeleteUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideFeedWriteUseCase(repo: FeedRepo): RequestFeedWriteUseCase {
        return RequestFeedWriteUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideFeedLikeUseCase(repo: FeedRepo): RequestFeedLikeUseCase {
        return RequestFeedLikeUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideQuizSolvedUseCase(repo: QuizRepo): QuizSolvedUseCase {
        return QuizSolvedUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideQuizReviewUseCase(repo: QuizRepo): QuizReviewUseCase {
        return QuizReviewUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideQuizSubmitOXUseCase(repo: QuizRepo): QuizSubmitOXUseCase {
        return QuizSubmitOXUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideQuizSubmitMultiUseCase(repo: QuizRepo): QuizSubmitMultiUseCase {
        return QuizSubmitMultiUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideImageUpload(repo: ImageUploadRepo): RequestImageUploadUseCase {
        return RequestImageUploadUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideImageModify(repo: ImageEditRepo): RequestImageModifyUseCase {
        return RequestImageModifyUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideImageDelete(repo: ImageEditRepo): RequestImageDeleteUseCase {
        return RequestImageDeleteUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideMyPost(repo: MyPostRepo): LoadMyPostUseCase {
        return LoadMyPostUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideFeedCommentWriteUseCase(repo: FeedRepo): RequestFeedCommentWriteUseCase {
        return RequestFeedCommentWriteUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideFeedCommentLikeUseCase(repo: FeedRepo): RequestFeedCommentLikeUseCase {
        return RequestFeedCommentLikeUseCase(repo)
    }

    @Singleton
    @Provides
    fun provideFeedCommentDeleteUseCase(repo: FeedRepo): RequestFeedCommentDeleteUseCase {
        return RequestFeedCommentDeleteUseCase(repo)
    }
}