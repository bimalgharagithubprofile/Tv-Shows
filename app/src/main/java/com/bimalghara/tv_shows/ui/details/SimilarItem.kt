package com.bimalghara.tv_shows.ui.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bimalghara.tv_shows.domain.model.TvShows
import com.bimalghara.tv_shows.ui.base.MyImage

@Composable
fun SimilarItem(tvSHow: TvShows, onItemClick: () -> Unit){
    Card(
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable {
                onItemClick()
            },
        elevation = 6.dp,
    ) {
        MyImage(
            height = 150.dp,
            url = tvSHow.posterPath
        )
    }
}