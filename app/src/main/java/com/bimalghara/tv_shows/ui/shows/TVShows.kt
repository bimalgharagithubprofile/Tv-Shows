package com.bimalghara.tv_shows.ui.shows

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.bimalghara.tv_shows.domain.model.DataStateWrapper
import com.bimalghara.tv_shows.ui.base.MyErrorMessage
import com.bimalghara.tv_shows.ui.base.MyProgressBar
import com.bimalghara.tv_shows.ui.base.MyTopAppBar

@ExperimentalAnimationApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TVShowsScreen(
    navController: NavController,
    viewModel: ShowsViewModel
) {
    val state = viewModel.state.collectAsState().value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MyTopAppBar()
        },
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
    ) {
        when (state) {
            is DataStateWrapper.Loading -> {
                MyProgressBar()
            }
            is DataStateWrapper.Error -> {
                MyErrorMessage(state.errorMsg!!)
            }
            is DataStateWrapper.Success -> {
                //
            }
            else -> Unit
        }
    }

}