package com.bimalghara.tv_shows.ui.utils

sealed class Screen(val route: String) {
    object TVShowsScreen: Screen("tv_shows_screen")
    object ShowDetailsScreen: Screen("show_details_screen")
}
