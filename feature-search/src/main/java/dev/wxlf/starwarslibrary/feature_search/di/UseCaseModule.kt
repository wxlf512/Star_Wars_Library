package dev.wxlf.starwarslibrary.feature_search.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.wxlf.starwarslibrary.core.data.repository.SWRepository
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.GetFilmUseCase
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
    fun provideGetFilmUseCase(swRepository: SWRepository) =
        GetFilmUseCase(swRepository)
}