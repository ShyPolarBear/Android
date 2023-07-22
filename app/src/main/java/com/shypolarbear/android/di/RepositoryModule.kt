package com.shypolarbear.android.di

import com.shypolarbear.data.repositoryimpl.ExampleRepoImpl
import com.shypolarbear.data.repositoryimpl.feed.FeedRepoImpl
import com.shypolarbear.domain.repository.ExampleRepo
import com.shypolarbear.domain.repository.feed.FeedRepo
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
    abstract fun bindFeedTotalRepo(repoImp: FeedRepoImpl): FeedRepo
}