package dev.wxlf.starwarslibrary.feature_search.domain.usecases

import dev.wxlf.starwarslibrary.core.data.retrofit.models.PersonModel
import dev.wxlf.starwarslibrary.core.data.repository.SWRepository

class SearchPeopleUseCase(private val swRepository: SWRepository) {

    sealed interface Result {
        data class Success(val people: List<PersonModel>) : Result
        data class Error(val msg: String) : Result
        object Loading : Result
    }

    suspend operator fun invoke(query: String): Result =
        try {
            Result.Success(swRepository.searchPeople(query).results)
        } catch (e: Exception) {
            Result.Error(e.localizedMessage.orEmpty())
        }
}