package dev.wxlf.starwarslibrary.feature_favorites.domain.usecases

import dev.wxlf.starwarslibrary.core.data.datasources.local.SWRoomDataSource
import dev.wxlf.starwarslibrary.core.data.datasources.remote.SWRetrofitDataSource
import dev.wxlf.starwarslibrary.core.data.repository.SWRepository
import dev.wxlf.starwarslibrary.core.data.repository.SWRepositoryImpl
import dev.wxlf.starwarslibrary.core.data.retrofit.SWAPI
import dev.wxlf.starwarslibrary.core.data.retrofit.models.FilmModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PersonModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PlanetModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.StarshipModel
import dev.wxlf.starwarslibrary.core.data.room.FavoritesDao
import dev.wxlf.starwarslibrary.core.data.room.entities.FavoriteEntity
import dev.wxlf.starwarslibrary.core.util.SearchType
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class UseCaseTest {

    private lateinit var swRepository: SWRepository
    private lateinit var deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase
    private lateinit var loadFavoritesUseCase: LoadFavoritesUseCase
    private lateinit var getPeopleUseCase: GetPeopleUseCase
    private lateinit var getStarshipsUseCase: GetStarshipsUseCase
    private lateinit var getPlanetsUseCase: GetPlanetsUseCase
    private lateinit var getFilmsUseCase: GetFilmsUseCase


    private val api: SWAPI = mock(SWAPI::class.java)
    private val dao: FavoritesDao = mock(FavoritesDao::class.java)

    @Before
    fun setUp() {
        swRepository = SWRepositoryImpl(SWRetrofitDataSource(api), SWRoomDataSource(dao))
        deleteFromFavoritesUseCase = DeleteFromFavoritesUseCase(swRepository)
        loadFavoritesUseCase = LoadFavoritesUseCase(swRepository)
        getPeopleUseCase = GetPeopleUseCase(swRepository)
        getStarshipsUseCase = GetStarshipsUseCase(swRepository)
        getPlanetsUseCase = GetPlanetsUseCase(swRepository)
        getFilmsUseCase = GetFilmsUseCase(swRepository)
    }

    @Test
    fun testDeleteFromFavoritesUseCase() {
        runBlocking {
            val testFavoriteEntity = FavoriteEntity("", "")
            Mockito.`when`(swRepository.deleteFromFavorites(testFavoriteEntity)).thenThrow(RuntimeException(""))
            assertTrue(deleteFromFavoritesUseCase(testFavoriteEntity) is DeleteFromFavoritesUseCase.Result.Error)
        }
    }

    @Test
    fun testLoadFavoritesUseCase() {
        runBlocking {
            val testFavorites = listOf(FavoriteEntity("a", "b"))

            Mockito.`when`(swRepository.loadFavorites(SearchType.PEOPLE)).thenReturn(testFavorites)
            Mockito.`when`(swRepository.loadFavorites(SearchType.STARSHIPS)).thenThrow(RuntimeException(""))

            assertEquals(loadFavoritesUseCase(SearchType.PEOPLE), LoadFavoritesUseCase.Result.Success(
                listOf("a")
            ))
            assertTrue(loadFavoritesUseCase(SearchType.STARSHIPS) is LoadFavoritesUseCase.Result.Error)
        }
    }

    @Test
    fun testGetPeopleUseCase() {
        runBlocking {
            val testPerson = PersonModel(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                arrayListOf(),
                arrayListOf(),
                arrayListOf(),
                arrayListOf()
            )

            Mockito.`when`(swRepository.getPerson(1)).thenReturn(testPerson)
            Mockito.`when`(swRepository.getPerson(2)).thenThrow(RuntimeException(""))

            assertEquals(getPeopleUseCase(listOf("aaa1aaa")), GetPeopleUseCase.Result.Success(
                listOf(testPerson)
            ))
            assertTrue(getPeopleUseCase(listOf("aaa2bbb")) is GetPeopleUseCase.Result.Error)
        }
    }

    @Test
    fun testGetStarshipsUseCase() {
        runBlocking {
            val testStarship = StarshipModel(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                arrayListOf(),
                arrayListOf(),
                "",
                ""
            )

            Mockito.`when`(swRepository.getStarship(1)).thenReturn(testStarship)
            Mockito.`when`(swRepository.getStarship(2)).thenThrow(RuntimeException(""))

            assertEquals(getStarshipsUseCase(listOf("aaa1aaa")), GetStarshipsUseCase.Result.Success(
                listOf(testStarship)
            ))
            assertTrue(getStarshipsUseCase(listOf("aaa2bbb")) is GetStarshipsUseCase.Result.Error)
        }
    }

    @Test
    fun testGetPlanetsUseCase() {
        runBlocking {
            val testPlanet = PlanetModel(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                arrayListOf(),
                arrayListOf()
            )

            Mockito.`when`(swRepository.getPlanet(1)).thenReturn(testPlanet)
            Mockito.`when`(swRepository.getPlanet(2)).thenThrow(RuntimeException(""))

            assertEquals(getPlanetsUseCase(listOf("aaa1aaa")), GetPlanetsUseCase.Result.Success(
                listOf(testPlanet)
            ))
            assertTrue(getPlanetsUseCase(listOf("aaa2bbb")) is GetPlanetsUseCase.Result.Error)
        }
    }

    @Test
    fun testGetFilmsUseCase() {
        runBlocking {
            val testFilm = FilmModel(
                "",
                1,
                "",
                "",
                "",
                "",
                "",
                "",
                arrayListOf(),
                arrayListOf(),
                arrayListOf(),
                arrayListOf(),
                arrayListOf()
            )

            Mockito.`when`(swRepository.getFilm(1)).thenReturn(testFilm)
            Mockito.`when`(swRepository.getFilm(2)).thenThrow(RuntimeException(""))

            assertEquals(getFilmsUseCase(listOf("aaa1aaa")), GetFilmsUseCase.Result.Success(
                listOf(testFilm)
            ))
            assertTrue(getFilmsUseCase(listOf("aaa2bbb")) is GetFilmsUseCase.Result.Error)
        }
    }
}