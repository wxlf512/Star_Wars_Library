package dev.wxlf.starwarslibrary.feature_search.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.wxlf.starwarslibrary.core.data.room.entities.FavoriteEntity
import dev.wxlf.starwarslibrary.core.util.SearchType
import dev.wxlf.starwarslibrary.core.util.SearchType.PEOPLE
import dev.wxlf.starwarslibrary.core.util.SearchType.PLANETS
import dev.wxlf.starwarslibrary.core.util.SearchType.STARSHIPS
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.AddToFavoritesUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.DeleteFromFavoritesUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.LoadFavoritesUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.SearchPeopleUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.SearchPlanetsUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.SearchStarshipsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchPeopleUseCase: SearchPeopleUseCase,
    private val searchStarshipsUseCase: SearchStarshipsUseCase,
    private val searchPlanetsUseCase: SearchPlanetsUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase,
    private val loadFavoritesUseCase: LoadFavoritesUseCase
) : ViewModel() {

    private val _searchPeopleState =
        MutableStateFlow<SearchPeopleUseCase.Result>(SearchPeopleUseCase.Result.Loading)
    val searchPeopleState = _searchPeopleState.asStateFlow()

    private val _searchStarshipsState =
        MutableStateFlow<SearchStarshipsUseCase.Result>(SearchStarshipsUseCase.Result.Loading)
    val searchStarshipsState = _searchStarshipsState.asStateFlow()

    private val _searchPlanetsState =
        MutableStateFlow<SearchPlanetsUseCase.Result>(SearchPlanetsUseCase.Result.Loading)
    val searchPlanetsState = _searchPlanetsState.asStateFlow()


    private val _addToFavoritesState =
        MutableStateFlow<AddToFavoritesUseCase.Result>(AddToFavoritesUseCase.Result.Loading)
    val addToFavoritesState = _addToFavoritesState.asStateFlow()

    private val _deleteFromFavoritesState =
        MutableStateFlow<DeleteFromFavoritesUseCase.Result>(DeleteFromFavoritesUseCase.Result.Loading)
    val deleteFromFavoritesState = _deleteFromFavoritesState.asStateFlow()

    private val _loadFavoritesState =
        MutableStateFlow<LoadFavoritesUseCase.Result>(LoadFavoritesUseCase.Result.Loading)
    val loadFavoritesState = _loadFavoritesState.asStateFlow()

    fun search(query: String, type: SearchType) =
        viewModelScope.launch(Dispatchers.IO) {
            if (query.length >= 2) {
                when (type) {
                    PEOPLE -> {
                        _searchPeopleState.emit(SearchPeopleUseCase.Result.Loading)
                        _searchPeopleState.emit(searchPeopleUseCase(query))
                    }

                    STARSHIPS -> {
                        _searchStarshipsState.emit(SearchStarshipsUseCase.Result.Loading)
                        _searchStarshipsState.emit(searchStarshipsUseCase(query))
                    }

                    PLANETS -> {
                        _searchPlanetsState.emit(SearchPlanetsUseCase.Result.Loading)
                        _searchPlanetsState.emit(searchPlanetsUseCase(query))
                    }
                }

                _loadFavoritesState.emit(LoadFavoritesUseCase.Result.Loading)
                _loadFavoritesState.emit(loadFavoritesUseCase(type))
            }
        }

    fun addToFavorites(url: String, type: SearchType) =
        viewModelScope.launch(Dispatchers.IO) {
            _addToFavoritesState.emit(AddToFavoritesUseCase.Result.Loading)
            _addToFavoritesState.emit(addToFavoritesUseCase(FavoriteEntity(url, type.name)))
            _loadFavoritesState.emit(LoadFavoritesUseCase.Result.Loading)
            _loadFavoritesState.emit(loadFavoritesUseCase(type))
        }

    fun deleteFromFavorites(url: String, type: SearchType) =
        viewModelScope.launch(Dispatchers.IO) {
            _deleteFromFavoritesState.emit(DeleteFromFavoritesUseCase.Result.Loading)
            _deleteFromFavoritesState.emit(deleteFromFavoritesUseCase(FavoriteEntity(url, type.name)))
            _loadFavoritesState.emit(LoadFavoritesUseCase.Result.Loading)
            _loadFavoritesState.emit(loadFavoritesUseCase(type))
        }
}