package dev.wxlf.starwarslibrary.feature_search.domain.usecases

import dev.wxlf.starwarslibrary.core.data.retrofit.models.PlanetModel
import dev.wxlf.starwarslibrary.core.data.repository.SWRepository

class SearchPlanetsUseCase(private val swRepository: SWRepository) {

    sealed interface Result {
        data class Success(val planets: List<PlanetModel>) : Result
        data class Error(val msg: String) : Result
        object Loading : Result
    }

    suspend operator fun invoke(query: String): Result =
        try {
            Result.Success(swRepository.searchPlanets(query).results)
        } catch (e: Exception) {
            Result.Error(e.localizedMessage.orEmpty())
        }
}