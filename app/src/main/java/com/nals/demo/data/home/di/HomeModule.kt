package com.nals.demo.data.home.di

import com.nals.demo.application.DemoApplicationDatabase
import com.nals.demo.data.home.*
import com.nals.demo.data.home.local.HomeLocalDataSource
import com.nals.demo.data.home.local.HomeLocalDataSourceImp
import com.nals.demo.data.home.remote.HomeRemoteDataSource
import com.nals.demo.data.home.remote.HomeRemoteDataSourceImp
import com.nals.demo.data.home.repository.HomeRepository
import com.nals.demo.data.home.repository.HomeRepositoryImp
import com.nals.demo.util.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HomeModule {

    @Provides
    @Singleton
    fun provideHomeLocalDataSource(demoApplicationDatabase: DemoApplicationDatabase): HomeLocalDataSource = HomeLocalDataSourceImp(demoApplicationDatabase.weatherDao())

    @Provides
    @Singleton
    fun provideHomeRemoteDataSource(apiService: ApiService): HomeRemoteDataSource = HomeRemoteDataSourceImp(apiService)

    @Provides
    @Singleton
    fun provideHomeRepository(
        homeLocalDataSource: HomeLocalDataSource,
        homeRemoteDataSource: HomeRemoteDataSource
    ): HomeRepository = HomeRepositoryImp(homeLocalDataSource, homeRemoteDataSource)
}