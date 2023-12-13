package com.bimalghara.tv_shows.domain.repository

import com.bimalghara.tv_shows.domain.model.TvShows


interface TVShowsRepositorySource {

    suspend fun getTVShowsList(): List<TvShows>

    suspend fun getShowDetails(id:Int): String

}