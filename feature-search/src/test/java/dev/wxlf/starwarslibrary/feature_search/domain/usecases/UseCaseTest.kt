package dev.wxlf.starwarslibrary.feature_search.domain.usecases

import dev.wxlf.starwarslibrary.core.data.datasources.local.SWRoomDataSource
import dev.wxlf.starwarslibrary.core.data.datasources.remote.SWRetrofitDataSource
import dev.wxlf.starwarslibrary.core.data.repository.SWRepository
import dev.wxlf.starwarslibrary.core.data.repository.SWRepositoryImpl
import dev.wxlf.starwarslibrary.core.data.retrofit.SWAPI
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PersonModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PlanetModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.SWModel
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
    private lateinit var searchPeopleUseCase: SearchPeopleUseCase
    private lateinit var searchStarshipsUseCase: SearchStarshipsUseCase
    private lateinit var searchPlanetsUseCase: SearchPlanetsUseCase
    private lateinit var addToFavoritesUseCase: AddToFavoritesUseCase
    private lateinit var deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase
    private lateinit var loadFavoritesUseCase: LoadFavoritesUseCase


    private val api: SWAPI = mock(SWAPI::class.java)
    private val dao: FavoritesDao = mock(FavoritesDao::class.java)

    @Before
    fun setUp() {
        swRepository = SWRepositoryImpl(SWRetrofitDataSource(api), SWRoomDataSource(dao))
        searchPeopleUseCase = SearchPeopleUseCase(swRepository)
        searchStarshipsUseCase = SearchStarshipsUseCase(swRepository)
        searchPlanetsUseCase = SearchPlanetsUseCase(swRepository)
        addToFavoritesUseCase = AddToFavoritesUseCase(swRepository)
        deleteFromFavoritesUseCase = DeleteFromFavoritesUseCase(swRepository)
        loadFavoritesUseCase = LoadFavoritesUseCase(swRepository)
    }

    @Test
    fun testSearchPeopleUseCase() {
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

            Mockito.`when`(swRepository.searchPeople("aa", 1))
                .thenReturn(SWModel(1, null, null, arrayListOf(testPerson)))

            Mockito.`when`(swRepository.searchPeople("bb", 1)).thenThrow(RuntimeException(""))

            assertEquals(
                searchPeopleUseCase("aa"),
                SearchPeopleUseCase.Result.Success(arrayListOf(testPerson))
            )
            assertTrue(searchPeopleUseCase("bb") is SearchPeopleUseCase.Result.Error)
        }
    }

    @Test
    fun testSearchStarshipsUseCase() {
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

            Mockito.`when`(swRepository.searchStarships("aa", 1))
                .thenReturn(SWModel(1, null, null, arrayListOf(testStarship)))

            Mockito.`when`(swRepository.searchStarships("bb", 1)).thenThrow(RuntimeException(""))

            assertEquals(
                searchStarshipsUseCase("aa"),
                SearchStarshipsUseCase.Result.Success(arrayListOf(testStarship))
            )
            assertTrue(searchStarshipsUseCase("bb") is SearchStarshipsUseCase.Result.Error)
        }
    }

    @Test
    fun testSearchPlanetsUseCase() {
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

            Mockito.`when`(swRepository.searchPlanets("aa", 1))
                .thenReturn(SWModel(1, null, null, arrayListOf(testPlanet)))

            Mockito.`when`(swRepository.searchPlanets("bb", 1)).thenThrow(RuntimeException(""))

            assertEquals(
                searchPlanetsUseCase("aa"),
                SearchPlanetsUseCase.Result.Success(arrayListOf(testPlanet))
            )
            assertTrue(searchPlanetsUseCase("bb") is SearchPlanetsUseCase.Result.Error)
        }
    }

    @Test
    fun testAddToFavoritesUseCase() {
        runBlocking {
            val testFavoriteEntity = FavoriteEntity("", "")
            Mockito.`when`(swRepository.addToFavorites(testFavoriteEntity)).thenThrow(RuntimeException(""))
            assertTrue(addToFavoritesUseCase(testFavoriteEntity) is AddToFavoritesUseCase.Result.Error)
        }
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
}