package dev.wxlf.starwarslibrary.core.data.repository

import dev.wxlf.starwarslibrary.core.data.datasources.SWRemoteDataSource
import dev.wxlf.starwarslibrary.core.data.retrofit.models.FilmModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PersonModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PlanetModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.SWModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.StarshipModel

class SWRepositoryImpl(private val remote: SWRemoteDataSource) : SWRepository {
    override suspend fun searchPeople(query: String): SWModel<PersonModel> =
        remote.searchPeople(query)

    override suspend fun searchStarships(query: String): SWModel<StarshipModel> =
        remote.searchStarships(query)

    override suspend fun searchPlanets(query: String): SWModel<PlanetModel> =
        remote.searchPlanets(query)

    override suspend fun getFilm(id: Int): FilmModel =
        remote.getFilm(id)
}