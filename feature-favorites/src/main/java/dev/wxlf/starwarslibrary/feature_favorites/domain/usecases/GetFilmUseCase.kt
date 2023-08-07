package dev.wxlf.starwarslibrary.feature_favorites.domain.usecases

import dev.wxlf.starwarslibrary.core.data.repository.SWRepository
import dev.wxlf.starwarslibrary.core.data.retrofit.models.FilmModel

class GetFilmUseCase(private val swRepository: SWRepository) {

    sealed interface Result {
        data class Success(val filmModel: FilmModel) : Result
        data class Error(val msg: String) : Result
        object Loading : Result
    }

    suspend operator fun invoke(url: String): Result =
        try {
            val id = url.filter { it.isDigit() }.toInt()
            Result.Success(swRepository.getFilm(id))
        } catch (e: Exception) {
            Result.Error(e.localizedMessage.orEmpty())
        }
}