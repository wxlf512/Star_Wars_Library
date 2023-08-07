package dev.wxlf.starwarslibrary.feature_search.domain.usecases

import dev.wxlf.starwarslibrary.core.data.repository.SWRepository
import dev.wxlf.starwarslibrary.core.util.SearchType

class LoadFavoritesUseCase(private val swRepository: SWRepository) {

    sealed interface Result {
        data class Success(val favorites: List<String>) : Result
        data class Error(val msg: String) : Result
        object Loading : Result
    }

    suspend operator fun invoke(type: SearchType): Result =
        try {
            Result.Success(swRepository.loadFavorites(type).map { it.url })
        } catch (e: Exception) {
            Result.Error(e.localizedMessage.orEmpty())
        }
}