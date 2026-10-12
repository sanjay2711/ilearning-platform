package com.example.ilearning.di

import com.example.ilearning.data.repository.CourseRepository
import com.example.ilearning.domain.repository.CourseRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class CourseRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCourseRepository(impl: CourseRepositoryImpl): CourseRepository

}