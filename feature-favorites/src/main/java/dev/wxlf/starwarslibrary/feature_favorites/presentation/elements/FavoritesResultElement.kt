package dev.wxlf.starwarslibrary.feature_favorites.presentation.elements

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import dev.wxlf.starwarslibrary.core.ui.theme.StarWarsLibraryTheme
import dev.wxlf.starwarslibrary.core.util.SearchType
import dev.wxlf.starwarslibrary.core.util.SearchType.*
import dev.wxlf.starwarslibrary.feature_favorites.R
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.DeleteFromFavoritesUseCase
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.GetPeopleUseCase
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.GetPlanetsUseCase
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.GetStarshipsUseCase
import dev.wxlf.starwarslibrary.feature_favorites.domain.usecases.LoadFavoritesUseCase

@Composable
fun FavoritesResultElement(
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.imePadding(),
    deleteFromFavoritesState: DeleteFromFavoritesUseCase.Result,
    loadFavoritesState: LoadFavoritesUseCase.Result,
    getPeopleState: GetPeopleUseCase.Result,
    getStarshipsState: GetStarshipsUseCase.Result,
    getPlanetsState: GetPlanetsUseCase.Result,
    type: SearchType,
    deleteFromFavorites: (url: String, type: SearchType) -> Unit
) {
    val context = LocalContext.current
    val favorites = remember {
        mutableStateListOf<String>()
    }

    when (deleteFromFavoritesState) {
        is Error -> {
            Toast.makeText(
                context,
                stringResource(R.string.error_while_delete_from_favorites), Toast.LENGTH_SHORT
            ).show()
        }

        else -> {}
    }

    when (loadFavoritesState) {
        is LoadFavoritesUseCase.Result.Error -> {
            Box(modifier = modifier.fillMaxSize()) {
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
            favorites.clear()
            favorites.addAll(loadFavoritesState.favorites)
        }
    }
    when (type) {
        PEOPLE -> {
            when (getPeopleState) {
                is GetPeopleUseCase.Result.Error -> {
                    Box(modifier = modifier.fillMaxSize()) {
                        Text(
                            getPeopleState.msg,
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }
                }

                GetPeopleUseCase.Result.Loading -> {
                    Box(modifier = modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

                is GetPeopleUseCase.Result.Success -> {
                    if (getPeopleState.people.isNotEmpty())
                        LazyColumn(modifier = modifier.fillMaxSize()) {
                            items(getPeopleState.people) {
                                PersonElement(
                                    personModel = it,
                                    inFavorite = true
                                ) {
                                    deleteFromFavorites(it.url, type)
                                }
                            }
                        }
                    else
                        Box(modifier = modifier.fillMaxSize()) {
                            Text(
                                stringResource(R.string.nothing_found),
                                style = MaterialTheme.typography.headlineLarge,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(16.dp)
                            )
                        }
                }
            }
        }

        STARSHIPS -> {
            when (getStarshipsState) {
                is GetStarshipsUseCase.Result.Error -> {
                    Box(modifier = modifier.fillMaxSize()) {
                        Text(
                            getStarshipsState.msg,
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }
                }

                GetStarshipsUseCase.Result.Loading -> {
                    Box(modifier = modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

                is GetStarshipsUseCase.Result.Success -> {
                    if (getStarshipsState.starships.isNotEmpty())
                        LazyColumn(modifier = modifier.fillMaxSize()) {
                            items(getStarshipsState.starships) {
                                StarshipElement(
                                    starshipModel = it,
                                    inFavorite = true
                                ) {
                                    deleteFromFavorites(it.url, type)
                                }
                            }
                        }
                    else
                        Box(modifier = modifier.fillMaxSize()) {
                            Text(
                                stringResource(R.string.nothing_found),
                                style = MaterialTheme.typography.headlineLarge,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(16.dp)
                            )
                        }
                }
            }
        }

        PLANETS -> {
            when (getPlanetsState) {
                is GetPlanetsUseCase.Result.Error -> {
                    Box(modifier = modifier.fillMaxSize()) {
                        Text(
                            getPlanetsState.msg,
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }
                }

                GetPlanetsUseCase.Result.Loading -> {
                    Box(modifier = modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

                is GetPlanetsUseCase.Result.Success -> {
                    if (getPlanetsState.planets.isNotEmpty())
                        LazyColumn(modifier = modifier.fillMaxSize()) {
                            items(getPlanetsState.planets) {
                                PlanetElement(
                                    planetModel = it,
                                    inFavorite = true
                                ) {
                                    deleteFromFavorites(it.url, type)
                                }
                            }
                        }
                    else
                        Box(modifier = modifier.fillMaxSize()) {
                            Text(
                                stringResource(R.string.nothing_found),
                                style = MaterialTheme.typography.headlineLarge,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(16.dp)
                            )
                        }
                }
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
fun SearchResultElementPreview() {
    StarWarsLibraryTheme {
        Surface {
            FavoritesResultElement(
                deleteFromFavoritesState = DeleteFromFavoritesUseCase.Result.Loading,
                loadFavoritesState = LoadFavoritesUseCase.Result.Loading,
                deleteFromFavorites = { _, _ -> },
                getPeopleState = GetPeopleUseCase.Result.Loading,
                getStarshipsState = GetStarshipsUseCase.Result.Loading,
                getPlanetsState = GetPlanetsUseCase.Result.Loading,
                type = PEOPLE
            )
        }
    }
}