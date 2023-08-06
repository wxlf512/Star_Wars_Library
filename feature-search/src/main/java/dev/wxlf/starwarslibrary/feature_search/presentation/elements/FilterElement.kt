package dev.wxlf.starwarslibrary.feature_search.presentation.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import dev.wxlf.starwarslibrary.core.ui.theme.StarWarsLibraryTheme
import dev.wxlf.starwarslibrary.feature_search.R
import dev.wxlf.starwarslibrary.feature_search.presentation.util.SearchType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterElement(
    modifier: Modifier = Modifier,
    type: SearchType,
    changeType: (SearchType) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FilterChip(
            selected = type == SearchType.PEOPLE,
            onClick = { changeType(SearchType.PEOPLE) },
            label = { Text(text = stringResource(R.string.people)) },
            leadingIcon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = stringResource(R.string.people)
                )
            }
        )

        FilterChip(
            selected = type == SearchType.STARSHIPS,
            onClick = { changeType(SearchType.STARSHIPS) },
            label = { Text(text = stringResource(R.string.starships)) },
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.baseline_rocket_launch_24),
                    contentDescription = stringResource(R.string.starships)
                )
            }
        )

        FilterChip(
            selected = type == SearchType.PLANETS,
            onClick = { changeType(SearchType.PLANETS) },
            label = { Text(text = stringResource(R.string.planets)) },
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.baseline_public_24),
                    contentDescription = stringResource(R.string.planets)
                )
            }
        )
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
            FilterElement(type = type, changeType = {type = it})
        }
    }
}