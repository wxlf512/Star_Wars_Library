package dev.wxlf.starwarslibrary.core.data.repository

import dev.wxlf.starwarslibrary.core.data.datasources.local.SWLocalDataSource
import dev.wxlf.starwarslibrary.core.data.datasources.remote.SWRemoteDataSource
import dev.wxlf.starwarslibrary.core.data.retrofit.models.FilmModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PersonModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PlanetModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.SWModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.StarshipModel
import dev.wxlf.starwarslibrary.core.data.room.entities.FavoriteEntity
import dev.wxlf.starwarslibrary.core.util.SearchType

class SWRepositoryImpl(
    private val remote: SWRemoteDataSource,
    private val local: SWLocalDataSource
) : SWRepository {
    override suspend fun searchPeople(query: String): SWModel<PersonModel> =
        remote.searchPeople(query)

    override suspend fun searchStarships(query: String): SWModel<StarshipModel> =
        remote.searchStarships(query)

    override suspend fun searchPlanets(query: String): SWModel<PlanetModel> =
        remote.searchPlanets(query)

    override suspend fun getPerson(id: Int): PersonModel =
        remote.getPerson(id)

    override suspend fun getStarship(id: Int): StarshipModel =
        remote.getStarship(id)

    override suspend fun getPlanet(id: Int): PlanetModel =
        remote.getPlanet(id)

    override suspend fun getFilm(id: Int): FilmModel =
        remote.getFilm(id)


    override suspend fun addToFavorites(favoriteEntity: FavoriteEntity) =
        local.addToFavorites(favoriteEntity)

    override suspend fun deleteFromFavorites(favoriteEntity: FavoriteEntity) =
        local.deleteFromFavorites(favoriteEntity)

    override suspend fun loadFavorites(type: SearchType): List<FavoriteEntity> =
        local.loadFavorites(type)
}