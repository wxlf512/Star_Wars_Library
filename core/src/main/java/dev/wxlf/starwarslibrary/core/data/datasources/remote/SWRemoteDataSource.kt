package dev.wxlf.starwarslibrary.core.data.datasources.remote

import dev.wxlf.starwarslibrary.core.data.retrofit.models.FilmModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PersonModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PlanetModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.SWModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.StarshipModel

interface SWRemoteDataSource {

    suspend fun searchPeople(query: String): SWModel<PersonModel>

    suspend fun searchStarships(query: String): SWModel<StarshipModel>

    suspend fun searchPlanets(query: String): SWModel<PlanetModel>

    suspend fun getFilm(id: Int): FilmModel
}