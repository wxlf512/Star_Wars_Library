package dev.wxlf.starwarslibrary.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.wxlf.starwarslibrary.core.data.datasources.SWRemoteDataSource
import dev.wxlf.starwarslibrary.core.data.datasources.SWRetrofitDataSource
import dev.wxlf.starwarslibrary.core.data.repository.SWRepository
import dev.wxlf.starwarslibrary.core.data.repository.SWRepositoryImpl
import dev.wxlf.starwarslibrary.core.data.retrofit.SWAPI
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideSWRetrofitDataSource(swapi: SWAPI): SWRemoteDataSource = SWRetrofitDataSource(swapi)

    @Provides
    @Singleton
    fun provideSWRepository(swRemoteDataSource: SWRemoteDataSource): SWRepository =
        SWRepositoryImpl(swRemoteDataSource)
}