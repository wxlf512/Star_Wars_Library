package dev.wxlf.starwarslibrary.feature_search.domain.usecases

import dev.wxlf.starwarslibrary.core.data.retrofit.models.FilmModel
import dev.wxlf.starwarslibrary.core.data.repository.SWRepository

class GetFilmUseCase(private val swRepository: SWRepository) {

    sealed interface Result {
        data class Success(val film: FilmModel) : Result
        data class Error(val msg: String) : Result
        object Loading : Result
    }

    suspend operator fun invoke(id: Int): Result =
        try {
            Result.Success(swRepository.getFilm(id))
        } catch (e: Exception) {
            Result.Error(e.localizedMessage.orEmpty())
        }
}