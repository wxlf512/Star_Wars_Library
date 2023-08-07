package dev.wxlf.starwarslibrary.core.data.repository

import dev.wxlf.starwarslibrary.core.data.retrofit.models.FilmModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PersonModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PlanetModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.SWModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.StarshipModel
import dev.wxlf.starwarslibrary.core.data.room.entities.FavoriteEntity
import dev.wxlf.starwarslibrary.core.util.SearchType

interface SWRepository {

    suspend fun searchPeople(query: String, page: Int): SWModel<PersonModel>

    suspend fun searchStarships(query: String, page: Int): SWModel<StarshipModel>

    suspend fun searchPlanets(query: String, page: Int): SWModel<PlanetModel>

    suspend fun getPerson(id: Int): PersonModel

    suspend fun getStarship(id: Int): StarshipModel

    suspend fun getPlanet(id: Int): PlanetModel

    suspend fun getFilm(id: Int): FilmModel


    suspend fun addToFavorites(favoriteEntity: FavoriteEntity)

    suspend fun deleteFromFavorites(favoriteEntity: FavoriteEntity)

    suspend fun loadFavorites(type: SearchType): List<FavoriteEntity>
}