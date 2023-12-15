package com.bimalghara.tv_shows.data.model

import com.google.gson.annotations.SerializedName

data class ResponseTvShowDetails(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("seasons") var seasons: ArrayList<Seasons> = arrayListOf()

)

data class Seasons(

    @SerializedName("episode_count") var episodeCount: Int? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("season_number") var seasonNumber: Int? = null

)