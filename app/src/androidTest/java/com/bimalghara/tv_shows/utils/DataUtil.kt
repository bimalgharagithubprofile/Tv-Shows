package com.bimalghara.tv_shows.utils

import com.bimalghara.tv_shows.domain.model.TvShowsEntity

object DataUtil {
    val dummyTvShows = listOf(
        TvShowsEntity(
            id = 57243,
            name = "Doctor Who",
            overview = "The Doctor is a Time Lord",
            posterPath = "/4edFyasCrkH4MKs6H4mHqlrxA6b.jpg",
            popularity = 1403.927,
            isFavourite = true
        ),
        TvShowsEntity(
            id = 1429,
            name = "Attack on Titan",
            overview = "The Doctor and friends travel",
            posterPath = "/hTP1DtLGFamjfu8WqjnuQdP1n4i.jpg",
            popularity = 177.541,
            isFavourite = false
        )
    )
}