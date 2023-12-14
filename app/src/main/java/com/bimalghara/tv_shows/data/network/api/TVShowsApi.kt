package com.bimalghara.tv_shows.data.network.api

import com.bimalghara.tv_shows.data.model.ResponseTvShows
import com.bimalghara.tv_shows.data.network.SafeApiRequest
import com.bimalghara.tv_shows.data.network.retrofit.ApiServiceGenerator
import javax.inject.Inject

class TVShowsApi @Inject constructor(private val serviceGenerator: ApiServiceGenerator) :
    SafeApiRequest() {
    private val tvShowsService: TVShowsService =
        serviceGenerator.createApiService(TVShowsService::class.java)

    suspend fun getAllTVShows(): ResponseTvShows = apiRequest(tvShowsService::getAllTVShows)

    suspend fun searchAllTVShows(query: String): ResponseTvShows = apiRequest {
        tvShowsService.searchAllTVShows(query = query)
    }

    suspend fun getAllSimilarShows(id: Int): ResponseTvShows =
        apiRequest { tvShowsService.getSimilarShows(id) }
}
