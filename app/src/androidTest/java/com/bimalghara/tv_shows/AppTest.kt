package com.bimalghara.tv_shows

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.*
import com.bimalghara.tv_shows.common.utils.TestTags
import com.bimalghara.tv_shows.ui.MainActivity
import com.bimalghara.tv_shows.ui.details.DetailViewUiState
import com.bimalghara.tv_shows.ui.details.ShowDetailsScreen
import com.bimalghara.tv_shows.ui.details.ShowDetailsViewModel
import com.bimalghara.tv_shows.ui.shows.ShowsViewModel
import com.bimalghara.tv_shows.ui.shows.TVShowsScreen
import com.bimalghara.tv_shows.ui.theme.AppTheme
import com.bimalghara.tv_shows.ui.utils.Screen
import com.bimalghara.tv_shows.utils.DataStatus
import com.bimalghara.tv_shows.utils.TestUtil.dataStatusCloud
import com.bimalghara.tv_shows.utils.TestUtil.dataStatusLocal
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AppTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var context:Context

    @OptIn(ExperimentalAnimationApi::class)
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            AppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.TVShowsScreen.route
                ) {
                    composable(route = Screen.TVShowsScreen.route) {
                        val viewModel = hiltViewModel<ShowsViewModel>()
                        viewModel.loadData()
                        TVShowsScreen(navController = navController, viewModel = viewModel)
                    }
                    composable(
                        route = Screen.ShowDetailsScreen.route + "/{${DetailViewUiState.ARG_SHOW}}/{${DetailViewUiState.ARG_SIMILAR_SHOW}}",
                        arguments = listOf(
                            navArgument(
                                name = DetailViewUiState.ARG_SHOW
                            ) {
                                type = NavType.StringType
                            },
                            navArgument(
                                name = DetailViewUiState.ARG_SIMILAR_SHOW
                            ) {
                                type = NavType.BoolType
                            }
                        )
                    ) {
                        val viewModel = hiltViewModel<ShowDetailsViewModel>()
                        ShowDetailsScreen(navController = navController, viewModel = viewModel)
                    }
                }
            }
        }

        context = ApplicationProvider.getApplicationContext()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun loading_isVisible() {
        dataStatusLocal = DataStatus.EmptyResponse
        dataStatusCloud = DataStatus.EmptyResponse

        composeRule.onNodeWithTag(TestTags.PROGRESS_INDICATOR).assertIsDisplayed()
    }

    @Test
    fun loadShows_isGridVisible() {
        dataStatusLocal = DataStatus.Success
        dataStatusCloud = DataStatus.Success

        composeRule.onNodeWithTag(TestTags.PROGRESS_INDICATOR).assertDoesNotExist()
        composeRule.onAllNodesWithTag(TestTags.TV_SHOW_ITEM)[0].assertTextContains(value = "DOCTOR WHO", ignoreCase = false)
        composeRule.onAllNodesWithTag(TestTags.TV_SHOW_ITEM)[1].assertTextContains(value = "DOCTOR WHO", ignoreCase = false)
    }


}
