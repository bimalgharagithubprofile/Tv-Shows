package com.bimalghara.tv_shows.data.mapper

import com.bimalghara.tv_shows.data.model.ResponseTvShows
import com.bimalghara.tv_shows.domain.model.TvShows

fun ResponseTvShows.toDomain(): List<TvShows> {
    return results.map { cloudResponse ->
        TvShows(
            id = cloudResponse.id ?: 0,
            name = cloudResponse.name ?: "",
            posterPath = "https://image.tmdb.org/t/p/w500${cloudResponse.posterPath ?: ""}",
            popularity = cloudResponse.popularity ?: 0.0
        )
    }
}