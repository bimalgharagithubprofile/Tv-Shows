package com.bimalghara.tv_shows.data.model

import com.google.gson.annotations.SerializedName

data class ResponseTvShows (
    @SerializedName("page"          ) var page         : Int?               = null,
    @SerializedName("results"       ) var results      : ArrayList<Results> = arrayListOf(),
    @SerializedName("total_pages"   ) var totalPages   : Int?               = null,
    @SerializedName("total_results" ) var totalResults : Int?               = null

)

data class Results (

    @SerializedName("adult"             ) var adult            : Boolean?          = null,
    @SerializedName("backdrop_path"     ) var backdropPath     : String?           = null,
    @SerializedName("id"                ) var id               : Int?              = null,
    @SerializedName("name"              ) var name             : String?           = null,
    @SerializedName("original_language" ) var originalLanguage : String?           = null,
    @SerializedName("original_name"     ) var originalName     : String?           = null,
    @SerializedName("overview"          ) var overview         : String?           = null,
    @SerializedName("poster_path"       ) var posterPath       : String?           = null,
    @SerializedName("media_type"        ) var mediaType        : String?           = null,
    @SerializedName("genre_ids"         ) var genreIds         : ArrayList<Int>    = arrayListOf(),
    @SerializedName("popularity"        ) var popularity       : Double?           = null,
    @SerializedName("first_air_date"    ) var firstAirDate     : String?           = null,
    @SerializedName("vote_average"      ) var voteAverage      : Double?           = null,
    @SerializedName("vote_count"        ) var voteCount        : Int?              = null,
    @SerializedName("origin_country"    ) var originCountry    : ArrayList<String> = arrayListOf()

)