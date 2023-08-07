package dev.wxlf.starwarslibrary.feature_search.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import dev.wxlf.starwarslibrary.feature_search.R
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.AddToFavoritesUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.DeleteFromFavoritesUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.LoadFavoritesUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.SearchPeopleUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.SearchPlanetsUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.SearchStarshipsUseCase
import dev.wxlf.starwarslibrary.feature_search.presentation.elements.FilterElement
import dev.wxlf.starwarslibrary.feature_search.presentation.elements.SearchElement
import dev.wxlf.starwarslibrary.feature_search.presentation.elements.SearchResultElement
import dev.wxlf.starwarslibrary.feature_search.presentation.viewmodels.SearchViewModel

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    val searchPeopleState by viewModel.searchPeopleState.collectAsState()
    val searchStarshipsState by viewModel.searchStarshipsState.collectAsState()
    val searchPlanetsState by viewModel.searchPlanetsState.collectAsState()
    val addToFavoritesState by viewModel.addToFavoritesState.collectAsState()
    val deleteFromFavoritesState by viewModel.deleteFromFavoritesState.collectAsState()
    val loadFavoritesState by viewModel.loadFavoritesState.collectAsState()

    SearchScreenContent(
        searchPeopleState,
        searchStarshipsState,
        searchPlanetsState,
        addToFavoritesState,
        deleteFromFavoritesState,
        loadFavoritesState,
        addToFavorites = { url, type -> viewModel.addToFavorites(url, type) },
        deleteFromFavorites = { url, type -> viewModel.deleteFromFavorites(url, type) }
    ) { query, type -> viewModel.search(query, type) }
}

@Composable
fun SearchScreenContent(
    searchPeopleState: SearchPeopleUseCase.Result,
    searchStarshipsState: SearchStarshipsUseCase.Result,
    searchPlanetsState: SearchPlanetsUseCase.Result,
    addToFavoritesState: AddToFavoritesUseCase.Result,
    deleteFromFavoritesState: DeleteFromFavoritesUseCase.Result,
    loadFavoritesState: LoadFavoritesUseCase.Result,
    addToFavorites: (url: String, type: SearchType) -> Unit,
    deleteFromFavorites: (url: String, type: SearchType) -> Unit,
    search: (query: String, type: SearchType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var query by rememberSaveable { mutableStateOf("") }
        var type: SearchType by rememberSaveable { mutableStateOf(SearchType.PEOPLE) }

        SearchElement(query = query) {
            query = it
            search(query, type)
        }
        FilterElement(type = type) {
            type = it
            search(query, type)
        }

        if (query.length < 2)
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_placeholder),
                    modifier = Modifier.size(200.dp)
                )
                Text(stringResource(R.string.search_tip), textAlign = TextAlign.Center)
            }
        else
            SearchResultElement(
                searchPeopleState = searchPeopleState,
                searchStarshipsState = searchStarshipsState,
                searchPlanetsState = searchPlanetsState,
                addToFavoritesState = addToFavoritesState,
                deleteFromFavoritesState = deleteFromFavoritesState,
                loadFavoritesState = loadFavoritesState,
                addToFavorites = { url, favType -> addToFavorites(url, favType) },
                deleteFromFavorites = { url, favType -> deleteFromFavorites(url, favType) },
                type = type
            )
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
fun StarWarsContentPreview() {
    StarWarsLibraryTheme {
        Surface {
            SearchScreenContent(
                SearchPeopleUseCase.Result.Loading,
                SearchStarshipsUseCase.Result.Loading,
                SearchPlanetsUseCase.Result.Loading,
                AddToFavoritesUseCase.Result.Loading,
                DeleteFromFavoritesUseCase.Result.Loading,
                LoadFavoritesUseCase.Result.Loading,
                addToFavorites = { _, _ -> },
                deleteFromFavorites = { _, _ -> }
            ) { _, _ -> }
        }
    }
}