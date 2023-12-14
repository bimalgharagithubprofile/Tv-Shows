package com.bimalghara.tv_shows.ui.details

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bimalghara.tv_shows.R
import com.bimalghara.tv_shows.domain.model.DataStateWrapper
import com.bimalghara.tv_shows.ui.base.MyImage
import com.bimalghara.tv_shows.ui.utils.Screen
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ShowDetailsScreen(
    navController: NavController,
    viewModel: ShowDetailsViewModel
) {
    val state = viewModel.state.collectAsState().value
    val favourite = viewModel.favourite.collectAsState().value
    val stateSimilarShows = viewModel.stateSimilarShows.collectAsState().value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = {
                    Text(
                        text = state.show?.name ?: stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.favoriteClickHandle() }
                    ) {
                        if(favourite){
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Remove from favorites"
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "Add to favorites"
                            )
                        }
                    }
                }
            )
        },
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (!state.show?.posterPath.isNullOrEmpty()) {
                MyImage(height = 300.dp, url = state.show!!.posterPath)
            }
            if (!state.show?.overview.isNullOrEmpty()) {
                Text(
                    text = stringResource(id = R.string.descriptions),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = state.show!!.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 10.dp, end = 10.dp, top = 15.dp, bottom = 15.dp),
                    textAlign = TextAlign.Justify,
                )
            }
            when (stateSimilarShows) {
                is DataStateWrapper.Success -> {
                    Text(
                        text = stringResource(id = R.string.similar_shows),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                        textAlign = TextAlign.Start,
                    )
                    LazyRow(
                        modifier = Modifier
                            .padding(5.dp)
                    ) {
                        items(stateSimilarShows.data!!.size) { index ->
                            SimilarItem(stateSimilarShows.data[index]) {
                                val gson = Gson()
                                val showStr = gson.toJson(stateSimilarShows.data[index])
                                val encodedShowStr =
                                    URLEncoder.encode(showStr, StandardCharsets.UTF_8.toString())
                                navController.navigate(
                                    Screen.ShowDetailsScreen.route + "/$encodedShowStr"
                                )
                            }
                        }
                    }
                }
                else -> Unit
            }
        }
    }

}