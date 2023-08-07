package dev.wxlf.starwarslibrary.feature_search.domain.usecases

import dev.wxlf.starwarslibrary.core.data.datasources.SWRetrofitDataSource
import dev.wxlf.starwarslibrary.core.data.repository.SWRepository
import dev.wxlf.starwarslibrary.core.data.repository.SWRepositoryImpl
import dev.wxlf.starwarslibrary.core.data.retrofit.SWAPI
import dev.wxlf.starwarslibrary.core.data.retrofit.models.FilmModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PersonModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PlanetModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.SWModel
import dev.wxlf.starwarslibrary.core.data.retrofit.models.StarshipModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito
import org.mockito.Mockito.mock

class UseCaseTest {

    private lateinit var swRepository: SWRepository
    private lateinit var getFilmUseCase: GetFilmUseCase
    private lateinit var searchPeopleUseCase: SearchPeopleUseCase
    private lateinit var searchStarshipsUseCase: SearchStarshipsUseCase
    private lateinit var searchPlanetsUseCase: SearchPlanetsUseCase


    private val api: SWAPI = mock(SWAPI::class.java)

    @Before
    fun setUp() {
        swRepository = SWRepositoryImpl(SWRetrofitDataSource(api))
        getFilmUseCase = GetFilmUseCase(swRepository)
        searchPeopleUseCase = SearchPeopleUseCase(swRepository)
        searchStarshipsUseCase = SearchStarshipsUseCase(swRepository)
        searchPlanetsUseCase = SearchPlanetsUseCase(swRepository)
    }

    @Test
    fun testGetFilmUseCase() {
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

            Mockito.`when`(swRepository.getFilm(0)).thenReturn(
                testFilm
            )

            Mockito.`when`(swRepository.getFilm(1)).thenThrow(RuntimeException(""))

            assertEquals(getFilmUseCase(0), GetFilmUseCase.Result.Success(testFilm))
            assertTrue(getFilmUseCase(1) is GetFilmUseCase.Result.Error)
        }
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

            Mockito.`when`(swRepository.searchPeople("aa"))
                .thenReturn(SWModel(1, null, null, arrayListOf(testPerson)))

            Mockito.`when`(swRepository.searchPeople("bb")).thenThrow(RuntimeException(""))

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

            Mockito.`when`(swRepository.searchStarships("aa"))
                .thenReturn(SWModel(1, null, null, arrayListOf(testStarship)))

            Mockito.`when`(swRepository.searchStarships("bb")).thenThrow(RuntimeException(""))

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

            Mockito.`when`(swRepository.searchPlanets("aa"))
                .thenReturn(SWModel(1, null, null, arrayListOf(testPlanet)))

            Mockito.`when`(swRepository.searchPlanets("bb")).thenThrow(RuntimeException(""))

            assertEquals(
                searchPlanetsUseCase("aa"),
                SearchPlanetsUseCase.Result.Success(arrayListOf(testPlanet))
            )
            assertTrue(searchPlanetsUseCase("bb") is SearchPlanetsUseCase.Result.Error)
        }
    }
}