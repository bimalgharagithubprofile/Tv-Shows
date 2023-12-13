package com.bimalghara.tv_shows.data.network.api

import com.bimalghara.tv_shows.data.model.ResponseTvShows
import retrofit2.http.GET
import retrofit2.http.Path

interface TVShowsService {


    @GET("trending/tv/day?language=en-US")
    suspend fun getAllTVShows(): ResponseTvShows


    @GET("tv/{id}?language=en-US")
    suspend fun getShowDetails(@Path("id") id: Int): String


}

