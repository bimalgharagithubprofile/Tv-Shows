package com.bimalghara.tv_shows.data.mapper

import com.bimalghara.tv_shows.data.model.ResponseTvShows
import com.bimalghara.tv_shows.domain.model.TvShowsEntity

fun ResponseTvShows.toDomain(): List<TvShowsEntity> {
    return results.map { cloudResponse ->
        TvShowsEntity(
            id = cloudResponse.id ?: 0,
            name = cloudResponse.name ?: "",
            overview = cloudResponse.overview ?: "",
            posterPath = "https://image.tmdb.org/t/p/w500${cloudResponse.posterPath ?: ""}",
            popularity = cloudResponse.popularity ?: 0.0,
        )
    }
}