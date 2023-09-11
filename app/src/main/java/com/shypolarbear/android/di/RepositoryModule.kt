package com.shypolarbear.android.di

import com.shypolarbear.data.repositoryimpl.ExampleRepoImpl
import com.shypolarbear.data.repositoryimpl.ImageRepoImpl
import com.shypolarbear.data.repositoryimpl.InfoRepoImpl
import com.shypolarbear.data.repositoryimpl.JoinRepoImpl
import com.shypolarbear.data.repositoryimpl.LoginRepoImpl
import com.shypolarbear.data.repositoryimpl.TokenRepoImpl
import com.shypolarbear.data.repositoryimpl.feed.FeedRepoImpl
import com.shypolarbear.data.repositoryimpl.quiz.QuizRepoImpl
import com.shypolarbear.domain.repository.ExampleRepo
import com.shypolarbear.domain.repository.ImageRepo
import com.shypolarbear.domain.repository.InfoRepo
import com.shypolarbear.domain.repository.JoinRepo
import com.shypolarbear.domain.repository.LoginRepo
import com.shypolarbear.domain.repository.TokenRepo
import com.shypolarbear.domain.repository.feed.FeedRepo
import com.shypolarbear.domain.repository.quiz.QuizRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindExampleRepo(repoImp: ExampleRepoImpl): ExampleRepo

    @Binds
    abstract fun bindFeedRepo(repoImp: FeedRepoImpl): FeedRepo

    @Binds
    abstract fun bindTokenRepo(repoImp: TokenRepoImpl): TokenRepo

    @Binds
    abstract fun bindLoginRepo(repoImp: LoginRepoImpl): LoginRepo

    @Binds
    abstract fun bindJoinRepo(repoImp: JoinRepoImpl): JoinRepo

    @Binds
    abstract fun bindInfoRepo(repoImp: InfoRepoImpl): InfoRepo

    @Binds
    abstract fun bindQuizRepo(repoImp: QuizRepoImpl): QuizRepo

    @Binds
    abstract fun bindImageRepo(repoImp: ImageRepoImpl): ImageRepo
}