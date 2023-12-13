package com.bimalghara.tv_shows.ui.shows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.bimalghara.tv_shows.domain.model.TvShows
import com.bimalghara.tv_shows.ui.base.MyImage
import java.util.*

@Composable
fun GridItem(tvSHow: TvShows, onItemClick: () -> Unit) {
    Card(
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable {
                onItemClick()
            },
        elevation = 8.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            MyImage(url = tvSHow.posterPath)
            Text(
                text = tvSHow.name.uppercase(Locale.ROOT),
                style = MaterialTheme.typography.h4,
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
