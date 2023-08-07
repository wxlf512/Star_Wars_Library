package dev.wxlf.starwarslibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import dev.wxlf.starwarslibrary.core.util.Routes
import dev.wxlf.starwarslibrary.core.ui.theme.StarWarsLibraryTheme
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
                                    if (navController.currentDestination?.route == Routes.SEARCH.route)
                                        Text(getString(R.string.star_wars_library))
                                },
                                colors = TopAppBarDefaults.mediumTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    ) {
                        NavHost(
                            modifier = Modifier.padding(it),
                            navController = navController,
                            startDestination = Routes.SEARCH.route
                        ) {
                            composable(Routes.SEARCH.route) {
                                SearchScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}