package com.bimalghara.tv_shows.domain.repository

import com.bimalghara.tv_shows.domain.model.TvShows


interface TVShowsRepositorySource {

    suspend fun getTVShowsList(): List<TvShows>


    suspend fun getSimilarShowsList(id:Int): List<TvShows>

}