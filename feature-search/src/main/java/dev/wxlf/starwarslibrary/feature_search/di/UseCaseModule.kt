package dev.wxlf.starwarslibrary.feature_search.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.wxlf.starwarslibrary.core.data.repository.SWRepository
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.AddToFavoritesUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.DeleteFromFavoritesUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.LoadFavoritesUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.SearchPeopleUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.SearchPlanetsUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.SearchStarshipsUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideSearchPeopleUseCase(swRepository: SWRepository) =
        SearchPeopleUseCase(swRepository)

    @Provides
    @Singleton
    fun provideSearchStarshipsUseCase(swRepository: SWRepository) =
        SearchStarshipsUseCase(swRepository)

    @Provides
    @Singleton
    fun provideSearchPlanetsUseCase(swRepository: SWRepository) =
        SearchPlanetsUseCase(swRepository)

    @Provides
    @Singleton
    fun provideAddToFavoritesUseCase(swRepository: SWRepository) =
        AddToFavoritesUseCase(swRepository)

    @Provides
    @Singleton
    fun provideDeleteFromFavoritesUseCase(swRepository: SWRepository) =
        DeleteFromFavoritesUseCase(swRepository)

    @Provides
    @Singleton
    fun provideLoadFavoritesUseCase(swRepository: SWRepository) =
        LoadFavoritesUseCase(swRepository)
}