package dev.wxlf.starwarslibrary.feature_favorites.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.wxlf.starwarslibrary.core.ui.theme.StarWarsLibraryTheme
import dev.wxlf.starwarslibrary.core.util.SearchType
import dev.wxlf.starwarslibrary.feature_favorites.R
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.DeleteFromFavoritesUseCase
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.GetFilmsUseCase
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.GetPeopleUseCase
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.GetPlanetsUseCase
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.GetStarshipsUseCase
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.LoadFavoritesUseCase
import dev.wxlf.starwarslibrary.feature_favorites.presentation.elements.FavoritesResultElement
import dev.wxlf.starwarslibrary.feature_favorites.presentation.elements.FilterElement
import dev.wxlf.starwarslibrary.feature_favorites.presentation.viewmodels.FavoritesViewModel

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel = hiltViewModel()) {
    val deleteFromFavoritesState by viewModel.deleteFromFavoritesState.collectAsState()
    val loadFavoritesState by viewModel.loadFavoritesState.collectAsState()
    val getPeopleState by viewModel.getPeopleState.collectAsState()
    val getStarshipsState by viewModel.getStarshipsState.collectAsState()
    val getPlanetsState by viewModel.getPlanetsState.collectAsState()
    val getFilmsState by viewModel.getFilmsState.collectAsState()

    FavoritesScreenContent(
        deleteFromFavoritesState,
        loadFavoritesState,
        getPeopleState,
        getStarshipsState,
        getPlanetsState,
        getFilmsState,
        deleteFromFavorites = { url, type -> viewModel.deleteFromFavorites(url, type) },
        searchFilms = { viewModel.searchFilms() }
    ) { type -> viewModel.searchFavorites(type) }
}

@Composable
fun FavoritesScreenContent(
    deleteFromFavoritesState: DeleteFromFavoritesUseCase.Result,
    loadFavoritesState: LoadFavoritesUseCase.Result,
    getPeopleState: GetPeopleUseCase.Result,
    getStarshipsState: GetStarshipsUseCase.Result,
    getPlanetsState: GetPlanetsUseCase.Result,
    getFilmsState: GetFilmsUseCase.Result,
    deleteFromFavorites: (url: String, type: SearchType) -> Unit,
    searchFilms: () -> Unit,
    searchFavorites: (type: SearchType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var type: SearchType by rememberSaveable { mutableStateOf(SearchType.PEOPLE) }
        var films by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            searchFavorites(type)
        }

        FilterElement(type = type) { filterType, filterFilms ->
            type = filterType
            films = filterFilms
            if (!films)
                searchFavorites(type)
            else
                searchFilms()
        }

        when (loadFavoritesState) {
            is LoadFavoritesUseCase.Result.Error -> {
                Box(
                    modifier = Modifier
                        .imePadding()
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Text(
                        loadFavoritesState.msg,
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
            }

            LoadFavoritesUseCase.Result.Loading -> {}
            is LoadFavoritesUseCase.Result.Success -> {
                if (loadFavoritesState.favorites.isEmpty() && !films)
                    Column(
                        modifier = Modifier.fillMaxHeight().padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.FavoriteBorder,
                            contentDescription = stringResource(R.string.favorites_placeholder),
                            modifier = Modifier.size(200.dp)
                        )
                        Text(
                            stringResource(R.string.favorites_placeholder),
                            textAlign = TextAlign.Center
                        )
                    }
                else
                    FavoritesResultElement(
                        modifier = Modifier.imePadding().padding(8.dp),
                        deleteFromFavoritesState = deleteFromFavoritesState,
                        loadFavoritesState = loadFavoritesState,
                        getPeopleState = getPeopleState,
                        getStarshipsState = getStarshipsState,
                        getPlanetsState = getPlanetsState,
                        getFilmsState = getFilmsState,
                        type = type,
                        films = films,
                        deleteFromFavorites = { url, favType -> deleteFromFavorites(url, favType) }
                    )
            }
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    device = "spec:width=392.7dp,height=850.9dp,dpi=440",
)
@Composable
fun FavoritesScreenContentPreview() {
    StarWarsLibraryTheme {
        Surface {
            FavoritesScreenContent(
                DeleteFromFavoritesUseCase.Result.Loading,
                LoadFavoritesUseCase.Result.Loading,
                GetPeopleUseCase.Result.Loading,
                GetStarshipsUseCase.Result.Loading,
                GetPlanetsUseCase.Result.Loading,
                GetFilmsUseCase.Result.Loading,
                deleteFromFavorites = { _, _ -> },
                searchFilms = {}
            ) { _ -> }
        }
    }
}