package dev.wxlf.starwarslibrary.feature_search.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.GetFilmUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.SearchPeopleUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.SearchPlanetsUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.SearchStarshipsUseCase
import dev.wxlf.starwarslibrary.core.util.SearchType
import dev.wxlf.starwarslibrary.core.util.SearchType.*
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
    private val getFilmUseCase: GetFilmUseCase
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

    private val _getFilmState =
        MutableStateFlow<GetFilmUseCase.Result>(GetFilmUseCase.Result.Loading)
    val getFilmState = _getFilmState.asStateFlow()

    fun search(query: String, type: SearchType) =
        viewModelScope.launch(Dispatchers.IO) {
            if (query.length >= 2)
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
        }
}