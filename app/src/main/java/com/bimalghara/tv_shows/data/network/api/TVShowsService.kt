package com.bimalghara.tv_shows.data.network.api

import com.bimalghara.tv_shows.data.model.ResponseTvShows
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TVShowsService {


    @GET("trending/tv/week?language=en-US")
    suspend fun getAllTVShows(): ResponseTvShows


    @GET("search/tv")
    suspend fun searchAllTVShows(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): ResponseTvShows


    @GET("tv/{id}/similar?language=en-US&page=1")
    suspend fun getSimilarShows(@Path("id") id: Int): ResponseTvShows


}

