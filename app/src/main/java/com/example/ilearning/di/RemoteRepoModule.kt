package com.example.ilearning.di

import com.example.ilearning.data.repository.RemoteRepository
import com.example.ilearning.domain.repository.RemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteRepoModule {

    @Binds
    @Singleton
    abstract fun bindRemoteRepository(impl : RemoteRepositoryImpl) : RemoteRepository
}