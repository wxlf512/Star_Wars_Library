package dev.wxlf.starwarslibrary.feature_favorites.presentation.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import dev.wxlf.starwarslibrary.core.ui.theme.StarWarsLibraryTheme
import dev.wxlf.starwarslibrary.core.util.SearchType
import dev.wxlf.starwarslibrary.feature_favorites.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterElement(
    modifier: Modifier = Modifier,
    type: SearchType,
    changeType: (SearchType, Boolean) -> Unit
) {
    var films by rememberSaveable {
        mutableStateOf(false)
    }
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        item {
            FilterChip(
                selected = type == SearchType.PEOPLE && !films,
                onClick = {
                    changeType(SearchType.PEOPLE, false)
                    films = false
                },
                label = { Text(text = stringResource(R.string.people)) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = stringResource(R.string.people)
                    )
                }
            )
        }
        item {
            FilterChip(
                selected = type == SearchType.STARSHIPS && !films,
                onClick = {
                    changeType(SearchType.STARSHIPS, false)
                    films = false
                },
                label = { Text(text = stringResource(R.string.starships)) },
                leadingIcon = {
                    Icon(
                        painterResource(id = R.drawable.baseline_rocket_launch_24),
                        contentDescription = stringResource(R.string.starships)
                    )
                }
            )
        }

        item {
            FilterChip(
                selected = type == SearchType.PLANETS && !films,
                onClick = {
                    changeType(SearchType.PLANETS, false)
                    films = true
                },
                label = { Text(text = stringResource(R.string.planets)) },
                leadingIcon = {
                    Icon(
                        painterResource(id = R.drawable.baseline_public_24),
                        contentDescription = stringResource(R.string.planets)
                    )
                }
            )
        }

        item {
            FilterChip(
                selected = films,
                onClick = {
                    changeType(SearchType.PEOPLE, true)
                    films = true
                },
                label = { Text(text = stringResource(R.string.films)) },
                leadingIcon = {
                    Icon(
                        painterResource(id = R.drawable.baseline_movie_24),
                        contentDescription = stringResource(R.string.films)
                    )
                }
            )
        }
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
)
@Composable
fun FilterElementPreview() {
    StarWarsLibraryTheme {
        Surface {
            var type: SearchType by rememberSaveable { mutableStateOf(SearchType.PEOPLE) }
            FilterElement(type = type, changeType = { type1, _ -> type = type1 })
        }
    }
}