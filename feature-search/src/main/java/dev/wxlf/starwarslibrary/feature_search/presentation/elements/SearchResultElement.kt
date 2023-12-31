package dev.wxlf.starwarslibrary.feature_search.presentation.elements

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
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PersonModel
import dev.wxlf.starwarslibrary.core.ui.theme.StarWarsLibraryTheme
import dev.wxlf.starwarslibrary.core.util.SearchType
import dev.wxlf.starwarslibrary.core.util.SearchType.PEOPLE
import dev.wxlf.starwarslibrary.core.util.SearchType.PLANETS
import dev.wxlf.starwarslibrary.core.util.SearchType.STARSHIPS
import dev.wxlf.starwarslibrary.feature_search.R
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.AddToFavoritesUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.DeleteFromFavoritesUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.LoadFavoritesUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.SearchPeopleUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.SearchPlanetsUseCase
import dev.wxlf.starwarslibrary.feature_search.domain.usecases.SearchStarshipsUseCase

@Composable
fun SearchResultElement(
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.imePadding(),
    searchPeopleState: SearchPeopleUseCase.Result,
    searchStarshipsState: SearchStarshipsUseCase.Result,
    searchPlanetsState: SearchPlanetsUseCase.Result,
    addToFavoritesState: AddToFavoritesUseCase.Result,
    deleteFromFavoritesState: DeleteFromFavoritesUseCase.Result,
    loadFavoritesState: LoadFavoritesUseCase.Result,
    type: SearchType,
    addToFavorites: (url: String, type: SearchType) -> Unit,
    deleteFromFavorites: (url: String, type: SearchType) -> Unit
) {
    val context = LocalContext.current
    val favorites = remember {
        mutableStateListOf<String>()
    }

    when (addToFavoritesState) {
        is Error -> {
            Toast.makeText(
                context,
                stringResource(R.string.error_while_add_to_favorites), Toast.LENGTH_SHORT
            ).show()
        }

        else -> {}
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
            when (searchPeopleState) {
                is SearchPeopleUseCase.Result.Error -> {
                    Box(modifier = modifier.fillMaxSize()) {
                        Text(
                            searchPeopleState.msg,
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }
                }

                SearchPeopleUseCase.Result.Loading -> {
                    Box(modifier = modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

                is SearchPeopleUseCase.Result.Success -> {
                    if (searchPeopleState.people.isNotEmpty())
                        LazyColumn(modifier = modifier.fillMaxSize()) {
                            items(searchPeopleState.people) {
                                PersonElement(
                                    personModel = it,
                                    inFavorite = it.url in favorites
                                ) {
                                    if (it.url in favorites) {
                                        deleteFromFavorites(it.url, type)
                                    } else
                                        addToFavorites(it.url, type)
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
            when (searchStarshipsState) {
                is SearchStarshipsUseCase.Result.Error -> {
                    Box(modifier = modifier.fillMaxSize()) {
                        Text(
                            searchStarshipsState.msg,
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }
                }

                SearchStarshipsUseCase.Result.Loading -> {
                    Box(modifier = modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

                is SearchStarshipsUseCase.Result.Success -> {
                    if (searchStarshipsState.starships.isNotEmpty())
                        LazyColumn(modifier = modifier.fillMaxSize()) {
                            items(searchStarshipsState.starships) {
                                StarshipElement(
                                    starshipModel = it,
                                    inFavorite = it.url in favorites
                                ) {
                                    if (it.url in favorites) {
                                        deleteFromFavorites(it.url, type)
                                    } else
                                        addToFavorites(it.url, type)
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
            when (searchPlanetsState) {
                is SearchPlanetsUseCase.Result.Error -> {
                    Box(modifier = modifier.fillMaxSize()) {
                        Text(
                            searchPlanetsState.msg,
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }
                }

                SearchPlanetsUseCase.Result.Loading -> {
                    Box(modifier = modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

                is SearchPlanetsUseCase.Result.Success -> {
                    if (searchPlanetsState.planets.isNotEmpty())
                        LazyColumn(modifier = modifier.fillMaxSize()) {
                            items(searchPlanetsState.planets) {
                                PlanetElement(
                                    planetModel = it,
                                    inFavorite = it.url in favorites
                                ) {
                                    if (it.url in favorites) {
                                        deleteFromFavorites(it.url, type)
                                    } else
                                        addToFavorites(it.url, type)
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
            val previewPerson = PersonModel(
                "Test Person",
                "11 BBY",
                "Male",
                "Blond",
                "180",
                "https://swapi.dev/api/planets/1/",
                "80",
                "Fair",
                "2014-12-09T13:50:51.644000Z",
                "2014-12-10T13:52:43.172000Z",
                "https://swapi.dev/api/people/1/",
                arrayListOf("https://swapi.dev/api/films/1/", "https://swapi.dev/api/films/2/"),
                arrayListOf("https://swapi.dev/api/species/1/", "https://swapi.dev/api/species/2/"),
                arrayListOf(
                    "https://swapi.dev/api/starships/12/",
                    "https://swapi.dev/api/starships/13/"
                ),
                arrayListOf(
                    "https://swapi.dev/api/vehicles/14/",
                    "https://swapi.dev/api/vehicles/15/"
                )
            )

            SearchResultElement(
                searchPeopleState = SearchPeopleUseCase.Result.Success(
                    listOf(
                        previewPerson,
                        previewPerson,
                        previewPerson
                    )
                ),
                searchStarshipsState = SearchStarshipsUseCase.Result.Loading,
                searchPlanetsState = SearchPlanetsUseCase.Result.Loading,
                addToFavoritesState = AddToFavoritesUseCase.Result.Loading,
                deleteFromFavoritesState = DeleteFromFavoritesUseCase.Result.Loading,
                loadFavoritesState = LoadFavoritesUseCase.Result.Loading,
                addToFavorites = { _, _ -> },
                deleteFromFavorites = { _, _ -> },
                type = PEOPLE
            )
        }
    }
}