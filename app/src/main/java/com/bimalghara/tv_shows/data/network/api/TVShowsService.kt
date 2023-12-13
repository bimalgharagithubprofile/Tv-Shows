package com.bimalghara.tv_shows.data.network.api

import com.bimalghara.tv_shows.data.model.ResponseTvShows
import retrofit2.http.GET
import retrofit2.http.Path

interface TVShowsService {


    @GET("trending/tv/week?language=en-US")
    suspend fun getAllTVShows(): ResponseTvShows


    @GET("tv/{id}/similar?language=en-US&page=1")
    suspend fun getSimilarShows(@Path("id") id: Int): ResponseTvShows


}

