package com.shypolarbear.android.di

import com.shypolarbear.data.repositoryimpl.ExampleRepoImpl
import com.shypolarbear.domain.repository.ExampleRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindExampleRepo(repoImp: ExampleRepoImpl): ExampleRepo
}