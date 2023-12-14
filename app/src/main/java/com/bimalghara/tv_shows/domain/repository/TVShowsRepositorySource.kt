package com.bimalghara.tv_shows.domain.repository

import com.bimalghara.tv_shows.domain.model.TvShowsEntity
import kotlinx.coroutines.flow.Flow


interface TVShowsRepositorySource {

    suspend fun downloadTVShowsList()

    suspend fun getTVShowsList(): Flow<List<TvShowsEntity>>

    suspend fun searchTVShowsList(query: String): List<TvShowsEntity>

    suspend fun getSimilarShowsList(id:Int): List<TvShowsEntity>

}