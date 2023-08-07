package dev.wxlf.starwarslibrary.core.data.datasources.remote

import dev.wxlf.starwarslibrary.core.data.retrofit.SWAPI
import dev.wxlf.starwarslibrary.core.data.retrofit.models.FilmModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PersonModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PlanetModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.SWModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.StarshipModel

class SWRetrofitDataSource(private val swapi: SWAPI) : SWRemoteDataSource {
    override suspend fun searchPeople(query: String): SWModel<PersonModel> =
        swapi.searchPeople(query)

    override suspend fun searchStarships(query: String): SWModel<StarshipModel> =
        swapi.searchStarships(query)

    override suspend fun searchPlanets(query: String): SWModel<PlanetModel> =
        swapi.searchPlanets(query)

    override suspend fun getPerson(id: Int): PersonModel =
        swapi.getPerson(id)

    override suspend fun getStarship(id: Int): StarshipModel =
        swapi.getStarship(id)

    override suspend fun getPlanet(id: Int): PlanetModel =
        swapi.getPlanet(id)

    override suspend fun getFilm(id: Int): FilmModel =
        swapi.getFilm(id)
}