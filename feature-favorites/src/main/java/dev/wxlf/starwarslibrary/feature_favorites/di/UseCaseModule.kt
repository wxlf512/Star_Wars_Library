package dev.wxlf.starwarslibrary.feature_favorites.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.wxlf.starwarslibrary.core.data.repository.SWRepository
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.DeleteFromFavoritesUseCase
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.GetPeopleUseCase
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.GetPlanetsUseCase
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.GetStarshipsUseCase
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.LoadFavoritesUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideDeleteFromFavoritesUseCase(swRepository: SWRepository) =
        DeleteFromFavoritesUseCase(swRepository)

    @Provides
    @Singleton
    fun provideLoadFavoritesUseCase(swRepository: SWRepository) =
        LoadFavoritesUseCase(swRepository)

    @Provides
    @Singleton
    fun provideGetPeopleUseCase(swRepository: SWRepository) =
        GetPeopleUseCase(swRepository)

    @Provides
    @Singleton
    fun provideGetStarshipsUseCase(swRepository: SWRepository) =
        GetStarshipsUseCase(swRepository)

    @Provides
    @Singleton
    fun provideGetPlanetsUseCase(swRepository: SWRepository) =
        GetPlanetsUseCase(swRepository)
}