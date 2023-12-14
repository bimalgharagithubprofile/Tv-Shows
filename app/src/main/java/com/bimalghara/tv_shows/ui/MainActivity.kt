package com.bimalghara.tv_shows.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bimalghara.tv_shows.ui.details.DetailViewUiState.Companion.ARG_SHOW
import com.bimalghara.tv_shows.ui.details.ShowDetailsScreen
import com.bimalghara.tv_shows.ui.details.ShowDetailsViewModel
import com.bimalghara.tv_shows.ui.shows.ShowsViewModel
import com.bimalghara.tv_shows.ui.shows.TVShowsScreen
import com.bimalghara.tv_shows.ui.theme.AppTheme
import com.bimalghara.tv_shows.ui.utils.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController, startDestination = Screen.TVShowsScreen.route
                    ) {
                        composable(route = Screen.TVShowsScreen.route) {
                            val viewModel = hiltViewModel<ShowsViewModel>()
                            viewModel.loadData()
                            TVShowsScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(
                            route = Screen.ShowDetailsScreen.route + "/{$ARG_SHOW}",
                            arguments = listOf(
                                navArgument(
                                    name = ARG_SHOW
                                ) {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val viewModel = hiltViewModel<ShowDetailsViewModel>()
                            ShowDetailsScreen(navController = navController, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}