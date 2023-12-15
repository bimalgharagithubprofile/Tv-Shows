package com.bimalghara.tv_shows.data.mapper

import com.bimalghara.tv_shows.data.model.ResponseTvShowDetails
import com.bimalghara.tv_shows.domain.model.Season
import com.bimalghara.tv_shows.domain.model.TvShowDetails

fun ResponseTvShowDetails.toDomain(): TvShowDetails {

    val seasons = seasons.map { season ->
        Season(
            id = season.id ?: 0,
            name = season.name ?: "",
            seasonNumber = season.seasonNumber ?: 0,
            episodeCount = season.episodeCount ?: 0
        )
    }

    return TvShowDetails(
        id = id ?: 0,
        seasons = ArrayList(seasons)
    )
}