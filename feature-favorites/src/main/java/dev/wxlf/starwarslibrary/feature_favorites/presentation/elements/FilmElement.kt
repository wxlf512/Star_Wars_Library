package dev.wxlf.starwarslibrary.feature_favorites.presentation.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
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
import dev.wxlf.starwarslibrary.core.data.retrofit.models.FilmModel
import dev.wxlf.starwarslibrary.core.ui.theme.StarWarsLibraryTheme
import dev.wxlf.starwarslibrary.feature_favorites.R

@Composable
fun FilmElement(modifier: Modifier = Modifier, filmModel: FilmModel) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                filmModel.title,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge
            )
            Divider(Modifier.padding(bottom = 4.dp))
            Text(
                buildAnnotatedString {
                    withStyle(style = MaterialTheme.typography.titleMedium.toSpanStyle()) {
                        append(stringResource(R.string.director))
                    }
                    append(filmModel.director)
                }, textAlign = TextAlign.Center
            )
            Text(buildAnnotatedString {
                withStyle(style = MaterialTheme.typography.titleMedium.toSpanStyle()) {
                    append(stringResource(R.string.producer))
                }
                append(filmModel.producer)
            }, textAlign = TextAlign.Center)
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
fun FilmElementPreview() {
    StarWarsLibraryTheme {
        Surface {
            val previewFilm = FilmModel(
                "Preview Title",
                1,
                "It is a period of civil war.\n\nRebel spaceships, striking\n\nfrom a hidden base, have won\n\ntheir first victory against\n\nthe evil Galactic Empire.\n\n\n\nDuring the battle, Rebel\n\nspies managed to steal secret\r\nplans to the Empire's\n\nultimate weapon, the DEATH\n\nSTAR, an armored space\n\nstation with enough power\n\nto destroy an entire planet.\n\n\n\nPursued by the Empire's\n\nsinister agents, Princess\n\nLeia races home aboard her\n\nstarship, custodian of the\n\nstolen plans that can save her\n\npeople and restore\n\nfreedom to the galaxy....",
                "George Lucas",
                "Gary Kurtz, Rick McCallum",
                "https://swapi.dev/api/films/1/",
                "2014-12-10T14:23:31.880000Z",
                "2014-12-12T11:24:39.858000Z",
                arrayListOf("https://swapi.dev/api/species/1/", "https://swapi.dev/api/species/2/"),
                arrayListOf(
                    "https://swapi.dev/api/starships/1/",
                    "https://swapi.dev/api/starships/2/"
                ),
                arrayListOf(
                    "https://swapi.dev/api/vehicles/1/",
                    "https://swapi.dev/api/vehicles/2/"
                ),
                arrayListOf("https://swapi.dev/api/planets/1/", "https://swapi.dev/api/planets/2/"),
                arrayListOf("https://swapi.dev/api/people/1/", "https://swapi.dev/api/people/2/")
            )
            FilmElement(filmModel = previewFilm)
        }
    }
}