package dev.wxlf.starwarslibrary.feature_favorites.domain.usecases

import dev.wxlf.starwarslibrary.core.data.repository.SWRepository
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PersonModel

class GetPeopleUseCase(private val swRepository: SWRepository) {

    sealed interface Result {
        data class Success(val people: List<PersonModel>) : Result
        data class Error(val msg: String) : Result
        object Loading : Result
    }

    suspend operator fun invoke(urls: List<String>): Result =
        try {
            val result = mutableListOf<PersonModel>()
            urls.forEach { url ->
                val id = url.filter { it.isDigit() }.toInt()
                result.add(swRepository.getPerson(id))
            }
            Result.Success(result.toList())
        } catch (e: Exception) {
            Result.Error(e.localizedMessage.orEmpty())
        }
}