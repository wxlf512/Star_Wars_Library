package dev.wxlf.starwarslibrary.feature_favorites.domain.usecases

import dev.wxlf.starwarslibrary.core.data.repository.SWRepository
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PlanetModel

class GetPlanetsUseCase(private val swRepository: SWRepository) {

    sealed interface Result {
        data class Success(val planets: List<PlanetModel>) : Result
        data class Error(val msg: String) : Result
        object Loading : Result
    }

    suspend operator fun invoke(urls: List<String>): Result =
        try {
            val result = mutableListOf<PlanetModel>()
            urls.forEach { url ->
                val id = url.filter { it.isDigit() }.toInt()
                result.add(swRepository.getPlanet(id))
            }
            Result.Success(result.toList())
        } catch (e: Exception) {
            Result.Error(e.localizedMessage.orEmpty())
        }
}