package dev.wxlf.starwarslibrary.feature_search.domain.usecases

import dev.wxlf.starwarslibrary.core.data.repository.SWRepository
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PersonModel

class SearchPeopleUseCase(private val swRepository: SWRepository) {

    sealed interface Result {
        data class Success(val people: List<PersonModel>) : Result
        data class Error(val msg: String) : Result
        object Loading : Result
    }

    suspend operator fun invoke(query: String): Result =
        try {
            var page = 1
            var nextPage = true
            val result = mutableListOf<PersonModel>()
            while (nextPage) {
                val pageInfo = swRepository.searchPeople(query, page)
                result.addAll(pageInfo.results)
                nextPage = pageInfo.hasMore()
                if (nextPage)
                    page++
            }
            Result.Success(result.toList())
        } catch (e: Exception) {
            Result.Error(e.localizedMessage.orEmpty())
        }
}