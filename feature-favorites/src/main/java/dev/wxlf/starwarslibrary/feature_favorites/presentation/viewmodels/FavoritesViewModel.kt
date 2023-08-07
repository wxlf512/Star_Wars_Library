package dev.wxlf.starwarslibrary.feature_favorites.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.wxlf.starwarslibrary.core.data.room.entities.FavoriteEntity
import dev.wxlf.starwarslibrary.core.util.SearchType
import dev.wxlf.starwarslibrary.core.util.SearchType.PEOPLE
import dev.wxlf.starwarslibrary.core.util.SearchType.PLANETS
import dev.wxlf.starwarslibrary.core.util.SearchType.STARSHIPS
import dev.wxlf.starwarslibrary.feature_favorites.R
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.DeleteFromFavoritesUseCase
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.GetFilmsUseCase
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.GetPeopleUseCase
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.GetPlanetsUseCase
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.GetStarshipsUseCase
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.LoadFavoritesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase,
    private val loadFavoritesUseCase: LoadFavoritesUseCase,
    private val getPeopleUseCase: GetPeopleUseCase,
    private val getStarshipsUseCase: GetStarshipsUseCase,
    private val getPlanetsUseCase: GetPlanetsUseCase,
    private val getFilmsUseCase: GetFilmsUseCase
) : ViewModel() {

    private val _deleteFromFavoritesState =
        MutableStateFlow<DeleteFromFavoritesUseCase.Result>(DeleteFromFavoritesUseCase.Result.Loading)
    val deleteFromFavoritesState = _deleteFromFavoritesState.asStateFlow()

    private val _loadFavoritesState =
        MutableStateFlow<LoadFavoritesUseCase.Result>(LoadFavoritesUseCase.Result.Loading)
    val loadFavoritesState = _loadFavoritesState.asStateFlow()

    private val _getPeopleState =
        MutableStateFlow<GetPeopleUseCase.Result>(GetPeopleUseCase.Result.Loading)
    val getPeopleState = _getPeopleState.asStateFlow()

    private val _getStarshipsState =
        MutableStateFlow<GetStarshipsUseCase.Result>(GetStarshipsUseCase.Result.Loading)
    val getStarshipsState = _getStarshipsState.asStateFlow()

    private val _getPlanetsState =
        MutableStateFlow<GetPlanetsUseCase.Result>(GetPlanetsUseCase.Result.Loading)
    val getPlanetsState = _getPlanetsState.asStateFlow()

    private val _getFilmsState =
        MutableStateFlow<GetFilmsUseCase.Result>(GetFilmsUseCase.Result.Loading)
    val getFilmsState = _getFilmsState.asStateFlow()

    fun deleteFromFavorites(url: String, type: SearchType) =
        viewModelScope.launch(Dispatchers.IO) {
            _deleteFromFavoritesState.emit(DeleteFromFavoritesUseCase.Result.Loading)
            _deleteFromFavoritesState.emit(
                deleteFromFavoritesUseCase(
                    FavoriteEntity(
                        url,
                        type.name
                    )
                )
            )
            searchFavorites(type)
        }

    fun searchFavorites(type: SearchType) =
        viewModelScope.launch(Dispatchers.IO) {
            _loadFavoritesState.emit(LoadFavoritesUseCase.Result.Loading)
            val favorites = loadFavoritesUseCase(type)
            _loadFavoritesState.emit(favorites)
            if (favorites is LoadFavoritesUseCase.Result.Success) {
                val favoritesList = favorites.favorites
                when (type) {
                    PEOPLE -> {
                        _getPeopleState.emit(GetPeopleUseCase.Result.Loading)
                        _getPeopleState.emit(getPeopleUseCase(favoritesList))
                    }

                    STARSHIPS -> {
                        _getStarshipsState.emit(GetStarshipsUseCase.Result.Loading)
                        _getStarshipsState.emit(getStarshipsUseCase(favoritesList))
                    }

                    PLANETS -> {
                        _getPlanetsState.emit(GetPlanetsUseCase.Result.Loading)
                        _getPlanetsState.emit(getPlanetsUseCase(favoritesList))
                    }
                }
            }
        }

    fun searchFilms() =
        viewModelScope.launch(Dispatchers.IO) {
            _getFilmsState.emit(GetFilmsUseCase.Result.Loading)
            _loadFavoritesState.emit(LoadFavoritesUseCase.Result.Loading)
            val favoritePeople = loadFavoritesUseCase(PEOPLE)
            val favoriteStarships = loadFavoritesUseCase(STARSHIPS)
            val favoritePlanets = loadFavoritesUseCase(PLANETS)
            _loadFavoritesState.emit(LoadFavoritesUseCase.Result.Success(listOf()))
            if (favoritePeople is LoadFavoritesUseCase.Result.Success &&
                favoriteStarships is LoadFavoritesUseCase.Result.Success &&
                favoritePlanets is LoadFavoritesUseCase.Result.Success
            ) {
                val filmsList = mutableSetOf<String>()

                val people = getPeopleUseCase(favoritePeople.favorites)
                val starships = getStarshipsUseCase(favoriteStarships.favorites)
                val planets = getPlanetsUseCase(favoritePlanets.favorites)

                if (people is GetPeopleUseCase.Result.Success &&
                    starships is GetStarshipsUseCase.Result.Success &&
                    planets is GetPlanetsUseCase.Result.Success
                ) {
                    people.people.forEach {
                        filmsList.addAll(it.filmsUrls)
                    }
                    starships.starships.forEach {
                        filmsList.addAll(it.films)
                    }
                    planets.planets.forEach {
                        filmsList.addAll(it.filmsUrls)
                    }
                    _getFilmsState.emit(getFilmsUseCase(filmsList.toList()))
                }
            } else
                _getFilmsState.emit(GetFilmsUseCase.Result.ErrorRes(R.string.error))
        }
}