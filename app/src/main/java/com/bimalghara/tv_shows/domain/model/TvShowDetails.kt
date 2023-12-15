package com.bimalghara.tv_shows.domain.model


data class TvShowDetails(
    var id:Int? = 0,
    var seasons: ArrayList<Season> = arrayListOf()
)

data class Season(
    var id: Int,
    var name: String,
    var seasonNumber: Int,
    var episodeCount: Int
)
