package com.bimalghara.tv_shows.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TvShowsEntity(
    @PrimaryKey(autoGenerate = false)
    var id:Int,

    var name:String,
    var overview:String,
    var posterPath:String,
    var popularity:Double,
    var isFavourite:Boolean = false
)