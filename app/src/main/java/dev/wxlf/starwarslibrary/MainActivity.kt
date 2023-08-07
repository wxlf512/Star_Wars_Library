package dev.wxlf.starwarslibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import dev.wxlf.starwarslibrary.core.ui.theme.StarWarsLibraryTheme
import dev.wxlf.starwarslibrary.core.util.Routes
import dev.wxlf.starwarslibrary.feature_favorites.presentation.screens.FavoritesScreen
import dev.wxlf.starwarslibrary.feature_search.presentation.screens.SearchScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarWarsLibraryTheme {
                val navController = rememberNavController()
                val systemUiController = rememberSystemUiController()
                val darkMode = isSystemInDarkTheme()
                var currentDestination by rememberSaveable {
                    mutableStateOf(Routes.SEARCH.route)
                }

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = !darkMode
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    when (currentDestination) {
                                        Routes.SEARCH.route -> Text(getString(R.string.star_wars_library))
                                        Routes.FAVORITES.route -> Text(getString(R.string.favorites))
                                    }
                                },
                                colors = TopAppBarDefaults.mediumTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        },
                        bottomBar = {
                            NavigationBar {
                                NavigationBarItem(
                                    selected = currentDestination == Routes.SEARCH.route,
                                    onClick = { navController.navigate(Routes.SEARCH.route) },
                                    icon = {
                                        Icon(
                                            Icons.Default.Search,
                                            contentDescription = getString(R.string.search)
                                        )
                                    },
                                    label = { Text(getString(R.string.search)) })
                                NavigationBarItem(
                                    selected = currentDestination == Routes.FAVORITES.route,
                                    onClick = { navController.navigate(Routes.FAVORITES.route) },
                                    icon = {
                                        Icon(
                                            Icons.Default.Favorite,
                                            contentDescription = getString(R.string.favorites)
                                        )
                                    },
                                    label = { Text(getString(R.string.favorites)) })
                            }
                        }
                    ) {
                        NavHost(
                            modifier = Modifier.padding(it),
                            navController = navController,
                            startDestination = Routes.SEARCH.route
                        ) {
                            composable(Routes.SEARCH.route) {
                                currentDestination = Routes.SEARCH.route
                                SearchScreen()
                            }
                            composable(Routes.FAVORITES.route) {
                                currentDestination = Routes.FAVORITES.route
                                FavoritesScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}