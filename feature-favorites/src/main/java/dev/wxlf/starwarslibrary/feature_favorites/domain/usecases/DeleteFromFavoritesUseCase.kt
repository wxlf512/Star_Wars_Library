package dev.wxlf.starwarslibrary.feature_favorites.domain.usecases

import dev.wxlf.starwarslibrary.core.data.repository.SWRepository
import dev.wxlf.starwarslibrary.core.data.room.entities.FavoriteEntity

class DeleteFromFavoritesUseCase(private val swRepository: SWRepository) {

    sealed interface Result {
        object Success : Result
        data class Error(val msg: String) : Result
        object Loading : Result
    }

    suspend operator fun invoke(favoriteEntity: FavoriteEntity): Result =
        try {
            swRepository.deleteFromFavorites(favoriteEntity)
            Result.Success
        } catch (e: Exception) {
            Result.Error(e.localizedMessage.orEmpty())
        }
}