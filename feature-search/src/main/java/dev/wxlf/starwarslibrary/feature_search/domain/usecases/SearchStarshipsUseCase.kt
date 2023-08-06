package dev.wxlf.starwarslibrary.feature_search.domain.usecases

import dev.wxlf.starwarslibrary.core.data.retrofit.models.StarshipModel
import dev.wxlf.starwarslibrary.core.data.repository.SWRepository

class SearchStarshipsUseCase(private val swRepository: SWRepository) {

    sealed interface Result {
        data class Success(val starships: List<StarshipModel>) : Result
        data class Error(val msg: String) : Result
        object Loading : Result
    }

    suspend operator fun invoke(query: String): Result =
        try {
            Result.Success(swRepository.searchStarships(query).results)
        } catch (e: Exception) {
            Result.Error(e.localizedMessage.orEmpty())
        }
}