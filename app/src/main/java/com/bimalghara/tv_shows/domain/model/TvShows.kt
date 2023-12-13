package com.bimalghara.tv_shows.domain.model

data class TvShows(
    var id:Int,
    var name:String,
    var overview:String,
    var posterPath:String,
    var popularity:Double
)