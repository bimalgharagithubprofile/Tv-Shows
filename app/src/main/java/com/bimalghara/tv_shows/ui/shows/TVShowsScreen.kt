package com.bimalghara.tv_shows.ui.shows

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bimalghara.tv_shows.R
import com.bimalghara.tv_shows.domain.model.DataStateWrapper
import com.bimalghara.tv_shows.ui.base.MyErrorMessage
import com.bimalghara.tv_shows.ui.base.MyProgressBar
import com.bimalghara.tv_shows.ui.utils.Screen
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TVShowsScreen(
    navController: NavController,
    viewModel: ShowsViewModel
) {
    val stateSearchActive = viewModel.stateSearchActive.collectAsState().value
    val stateSearchText = viewModel.stateSearchText.collectAsState().value
    val state = viewModel.state.collectAsState().value

    val searchHistory = remember { mutableStateListOf<String>() }

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
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.onActiveChange(true) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.search_hint)
                        )
                    }
                }
            )
        },
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) { paddingValues ->

        if (stateSearchActive) {
            SearchBar(
                query = stateSearchText,
                onQueryChange = {
                    viewModel.onQueryChange(it)
                },
                onSearch = {
                    searchHistory.add(it)
                    viewModel.onSearch(it)
                },
                active = true,
                onActiveChange = {
                    viewModel.onActiveChange(it)
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.search_hint))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.search_hint)
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            if (stateSearchText.isNotEmpty()) {
                                viewModel.clearSearch()
                            } else {
                                viewModel.closeSearch()
                            }
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.search_close)
                    )
                }
            ) {
                // previously searched history
                searchHistory.forEach {
                    Row(modifier = Modifier.padding(all = 14.dp)) {
                        Icon(
                            modifier = Modifier.padding(end = 10.dp),
                            imageVector = Icons.Default.History,
                            contentDescription = stringResource(id = R.string.history)
                        )
                        Text(text = it)
                    }
                }
            }
        }

        when (state) {
            is DataStateWrapper.Loading -> {
                MyProgressBar()
            }
            is DataStateWrapper.Error -> {
                MyErrorMessage(state.errorMsg!!)
            }
            is DataStateWrapper.Success -> {
                if (state.data.isNullOrEmpty()) {
                    MyErrorMessage(stringResource(id = R.string.no_shows))
                } else {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        columns = GridCells.Fixed(2)
                    ) {
                        items(state.data.size) { index ->
                            GridItem(
                                tvSHow = state.data[index]
                            ) {
                                val gson = Gson()
                                val showStr = gson.toJson(state.data[index])
                                val encodedShowStr =
                                    URLEncoder.encode(showStr, StandardCharsets.UTF_8.toString())
                                navController.navigate(
                                    Screen.ShowDetailsScreen.route + "/$encodedShowStr/${false}"
                                )
                            }
                        }
                    }
                }
            }
            else -> Unit
        }
    }

}