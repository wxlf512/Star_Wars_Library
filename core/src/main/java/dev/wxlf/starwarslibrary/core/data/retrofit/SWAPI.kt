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
    suspend fun searchPeople(@Query("search") query: String, @Query("page") page: Int): SWModel<PersonModel>

    @GET("starships")
    suspend fun searchStarships(@Query("search") query: String, @Query("page") page: Int): SWModel<StarshipModel>

    @GET("planets")
    suspend fun searchPlanets(@Query("search") query: String, @Query("page") page: Int): SWModel<PlanetModel>

    @GET("people/{id}")
    suspend fun getPerson(@Path("id") id: Int): PersonModel

    @GET("starships/{id}")
    suspend fun getStarship(@Path("id") id: Int): StarshipModel

    @GET("planets/{id}")
    suspend fun getPlanet(@Path("id") id: Int): PlanetModel

    @GET("films/{id}")
    suspend fun getFilm(@Path("id") id: Int): FilmModel
}