package dev.wxlf.starwarslibrary.feature_favorites.presentation.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import dev.wxlf.starwarslibrary.core.data.retrofit.models.PersonModel
import dev.wxlf.starwarslibrary.core.ui.theme.StarWarsLibraryTheme
import dev.wxlf.starwarslibrary.feature_favorites.R

@Composable
fun PersonElement(modifier: Modifier = Modifier, personModel: PersonModel, inFavorite: Boolean, onFavoriteButtonClick: () -> Unit) {
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
                    personModel.name,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleLarge
                )
                Divider(Modifier.padding(bottom = 4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(stringResource(R.string.gender, personModel.gender))
                    Text(stringResource(R.string.starships_count, personModel.starshipsUrls.size))
                }
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
fun PersonElementPreview() {
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
            PersonElement(personModel = previewPerson, inFavorite = false) {}
        }
    }
}