package dev.wxlf.starwarslibrary.core.data.retrofit

import dev.wxlf.starwarslibrary.core.data.retrofit.models.FilmModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PersonModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PlanetModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.SWModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.StarshipModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SWAPI {

    @GET("people")
    suspend fun searchPeople(@Query("search") query: String): SWModel<PersonModel>

    @GET("starships")
    suspend fun searchStarships(@Query("search") query: String): SWModel<StarshipModel>

    @GET("planets")
    suspend fun searchPlanets(@Query("search") query: String): SWModel<PlanetModel>

    @GET("films/{id}")
    suspend fun getFilm(@Path("id") id: Int): FilmModel
}