package dev.wxlf.starwarslibrary.feature_favorites.domain.usecases

import androidx.annotation.StringRes
import dev.wxlf.starwarslibrary.core.data.repository.SWRepository
import dev.wxlf.starwarslibrary.core.data.retrofit.models.FilmModel

class GetFilmsUseCase(private val swRepository: SWRepository) {

    sealed interface Result {
        data class Success(val films: List<FilmModel>) : Result
        data class Error(val msg: String) : Result
        data class ErrorRes(@StringRes val msg: Int) : Result
        object Loading : Result
    }

    suspend operator fun invoke(urls: List<String>): Result =
        try {
            val result = mutableListOf<FilmModel>()
            urls.forEach { url ->
                val id = url.filter { it.isDigit() }.toInt()
                result.add(swRepository.getFilm(id))
            }
            Result.Success(result.toList())
        } catch (e: Exception) {
            Result.Error(e.localizedMessage.orEmpty())
        }
}