package dev.wxlf.starwarslibrary.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.wxlf.starwarslibrary.core.data.datasources.local.SWLocalDataSource
import dev.wxlf.starwarslibrary.core.data.datasources.local.SWRoomDataSource
import dev.wxlf.starwarslibrary.core.data.datasources.remote.SWRemoteDataSource
import dev.wxlf.starwarslibrary.core.data.datasources.remote.SWRetrofitDataSource
import dev.wxlf.starwarslibrary.core.data.repository.SWRepository
import dev.wxlf.starwarslibrary.core.data.repository.SWRepositoryImpl
import dev.wxlf.starwarslibrary.core.data.retrofit.SWAPI
import dev.wxlf.starwarslibrary.core.data.room.FavoritesDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideSWRetrofitDataSource(swapi: SWAPI): SWRemoteDataSource = SWRetrofitDataSource(swapi)

    @Provides
    @Singleton
    fun provideSWRoomDataSource(favoritesDao: FavoritesDao): SWLocalDataSource =
        SWRoomDataSource(favoritesDao)

    @Provides
    @Singleton
    fun provideSWRepository(
        swRemoteDataSource: SWRemoteDataSource,
        swLocalDataSource: SWLocalDataSource
    ): SWRepository =
        SWRepositoryImpl(swRemoteDataSource, swLocalDataSource)
}