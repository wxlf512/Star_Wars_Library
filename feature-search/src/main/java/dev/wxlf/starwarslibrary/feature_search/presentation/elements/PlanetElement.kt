package dev.wxlf.starwarslibrary.feature_search.presentation.elements

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PlanetModel
import dev.wxlf.starwarslibrary.core.ui.theme.StarWarsLibraryTheme
import dev.wxlf.starwarslibrary.feature_search.R

@Composable
fun PlanetElement(modifier: Modifier = Modifier, planetModel: PlanetModel, inFavorite: Boolean, onFavoriteButtonClick: () -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    planetModel.name,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleLarge
                )
                Divider(Modifier.padding(bottom = 4.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.planet_diameter, planetModel.diameter))
                    Text(stringResource(R.string.planet_population, planetModel.population))
                }
            }
            Icon(
                if (inFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = stringResource(R.string.favorite_button),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable { onFavoriteButtonClick() }
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
    device = "spec:width=392.7dp,height=850.9dp,dpi=440",
)
@Composable
fun PlanetElementPreview() {
    StarWarsLibraryTheme {
        Surface {
            val previewPlanet = PlanetModel(
                "Preview Planet",
                "10000",
                "1",
                "120000",
                "Arid",
                "Dessert",
                "2014-12-09T13:50:49.641000Z",
                "2014-12-15T13:48:16.167217Z",
                "https://swapi.dev/api/planets/1/",
                "23",
                "304",
                "1",
                arrayListOf("https://swapi.dev/api/people/1/", "https://swapi.dev/api/people/2/"),
                arrayListOf("https://swapi.dev/api/films/1/", "https://swapi.dev/api/films/2/")
            )
            PlanetElement(planetModel = previewPlanet, inFavorite = false) {}
        }
    }
}