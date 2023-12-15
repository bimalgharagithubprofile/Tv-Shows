package com.bimalghara.tv_shows

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
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
import com.bimalghara.tv_shows.utils.FailureType
import com.bimalghara.tv_shows.utils.TestUtil.dataStatusCloud
import com.bimalghara.tv_shows.utils.TestUtil.dataStatusLocal
import com.bimalghara.tv_shows.utils.TestUtil.failureType
import com.google.common.truth.Truth
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

    private var navController: NavHostController? = null
    private lateinit var context: Context

    @OptIn(ExperimentalAnimationApi::class)
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            AppTheme {
                navController = rememberNavController()
                NavHost(
                    navController = navController!!,
                    startDestination = Screen.TVShowsScreen.route
                ) {
                    composable(route = Screen.TVShowsScreen.route) {
                        val viewModel = hiltViewModel<ShowsViewModel>()
                        viewModel.loadData()
                        TVShowsScreen(navController = navController!!, viewModel = viewModel)
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
                        ShowDetailsScreen(navController = navController!!, viewModel = viewModel)
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
    fun loadShowsFrom_cached_isGridVisible() {
        dataStatusLocal = DataStatus.Success
        dataStatusCloud = DataStatus.EmptyResponse

        //waiting
        composeRule.waitUntil(timeoutMillis = 5000) {
            composeRule.onAllNodesWithTag(TestTags.TV_SHOW_ITEM).fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onAllNodesWithTag(TestTags.TV_SHOW_ITEM)[0].assertTextContains(
            value = "DOCTOR WHO",
            ignoreCase = false
        )
        composeRule.onAllNodesWithTag(TestTags.TV_SHOW_ITEM)[1].assertTextContains(
            value = "ATTACK ON TITAN",
            ignoreCase = false
        )
        composeRule.onNodeWithTag(TestTags.PROGRESS_INDICATOR).assertDoesNotExist()
    }

    @Test
    fun loadShowsFrom_cloud_then_cached_isGridVisible() {
        dataStatusLocal = DataStatus.EmptyResponse
        dataStatusCloud = DataStatus.Success

        composeRule.onNodeWithTag(TestTags.PROGRESS_INDICATOR).assertIsDisplayed()
        //waiting
        composeRule.waitUntil(timeoutMillis = 10000) {
            composeRule.onAllNodesWithTag(TestTags.TV_SHOW_ITEM).fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onAllNodesWithTag(TestTags.TV_SHOW_ITEM)[0].assertTextContains(
            value = "DOCTOR WHO",
            ignoreCase = false,
            substring = true
        )
        composeRule.onAllNodesWithTag(TestTags.TV_SHOW_ITEM)[1].assertTextContains(
            value = "ATTACK ON TITAN",
            ignoreCase = false,
            substring = true
        )
        composeRule.onNodeWithTag(TestTags.PROGRESS_INDICATOR).assertDoesNotExist()
    }

    @Test
    fun tryToLoadShows_ButErrorOf_Network_isVisible() {
        dataStatusLocal = DataStatus.EmptyResponse
        dataStatusCloud = DataStatus.Fail
        failureType = FailureType.Network

        composeRule.onNodeWithText(text = "no internet", ignoreCase = true, substring = true)
        composeRule.onNodeWithTag(TestTags.PROGRESS_INDICATOR).assertDoesNotExist()
    }

    @Test
    fun performClick_OnThumbnail_navigateToDetailsScreen() {
        dataStatusLocal = DataStatus.Success
        dataStatusCloud = DataStatus.EmptyResponse

        composeRule.onAllNodesWithTag(TestTags.TV_SHOW_ITEM)[1].performClick()

        val route = navController?.currentBackStackEntry?.destination?.route

        Truth.assertThat(route).isEqualTo(Screen.ShowDetailsScreen.route + "/{${DetailViewUiState.ARG_SHOW}}/{${DetailViewUiState.ARG_SIMILAR_SHOW}}")
    }


}
