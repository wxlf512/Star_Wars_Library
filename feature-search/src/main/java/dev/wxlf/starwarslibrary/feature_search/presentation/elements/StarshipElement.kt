package dev.wxlf.starwarslibrary.feature_search.presentation.elements

import android.content.res.Configuration
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import dev.wxlf.starwarslibrary.core.data.retrofit.models.StarshipModel
import dev.wxlf.starwarslibrary.core.ui.theme.StarWarsLibraryTheme
import dev.wxlf.starwarslibrary.feature_search.R

@Composable
fun StarshipElement(
    modifier: Modifier = Modifier,
    starshipModel: StarshipModel,
    inFavorite: Boolean,
    onFavoriteButtonClick: () -> Unit
) {
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
                    starshipModel.name,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleLarge
                )
                Divider(Modifier.padding(bottom = 4.dp))
                Text(
                    buildAnnotatedString {
                        withStyle(style = MaterialTheme.typography.titleMedium.toSpanStyle()) {
                            append(stringResource(R.string.starship_model))
                        }
                        append(starshipModel.model)
                    },
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = MaterialTheme.typography.titleMedium.toSpanStyle()) {
                            append(stringResource(R.string.starship_manufacturer))
                        }
                        append(starshipModel.manufacturer)
                    },
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Divider(Modifier.padding(vertical = 4.dp))
                Text(
                    stringResource(R.string.starship_passengers_count, starshipModel.passengers),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            IconButton(
                onClick = { onFavoriteButtonClick() },
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Icon(
                    if (inFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.favorite_button),
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
fun StarshipElementPreview() {
    StarWarsLibraryTheme {
        Surface {
            val previewStarship = StarshipModel(
                "10 MGLT",
                "10000",
                "3 years",
                "1000000",
                "2014-12-10T16:36:50.509000Z",
                "12345",
                "2014-12-10T16:36:50.509000Z",
                "4.0",
                "120000",
                "Imperial Department of Military Research, Sienar Fleet Systems",
                "n/a",
                "DS-1 Orbital Battle Station",
                "Death Star",
                "843342",
                arrayListOf("https://swapi.dev/api/films/1/"),
                arrayListOf(),
                "Deep Space Mobile Battlestation",
                "https://swapi.dev/api/starships/9/"
            )
            StarshipElement(starshipModel = previewStarship, inFavorite = false) {}
        }
    }
}