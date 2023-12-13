package com.bimalghara.tv_shows.ui.details

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
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
import java.util.*

@ExperimentalAnimationApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ShowDetailsScreen(
    navController: NavController,
    viewModel: ShowDetailsViewModel
) {
    val state = viewModel.state.collectAsState().value
    val stateSimilarShows = viewModel.stateSimilarShows.collectAsState().value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (!state.show?.posterPath.isNullOrEmpty()) {
                MyImage(height = 300.dp, url = state.show!!.posterPath)
            }
            if (!state.show?.overview.isNullOrEmpty()) {
                Text(
                    text = stringResource(id = R.string.descriptions),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, top = 10.dp),
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = state.show!!.overview,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 8.dp, end = 8.dp, top = 15.dp, bottom = 15.dp),
                    textAlign = TextAlign.Justify,
                )
            }
            when (stateSimilarShows) {
                is DataStateWrapper.Success -> {
                    Text(
                        text = stringResource(id = R.string.similar_shows),
                        style = MaterialTheme.typography.caption,
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
                            Card(
                                backgroundColor = MaterialTheme.colors.background,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth(),
                                elevation = 6.dp,
                            ) {
                                MyImage(
                                    height = 150.dp,
                                    url = stateSimilarShows.data[index].posterPath
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