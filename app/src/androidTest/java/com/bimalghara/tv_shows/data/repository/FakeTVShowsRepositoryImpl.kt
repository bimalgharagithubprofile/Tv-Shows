package com.bimalghara.tv_shows.data.repository

import com.bimalghara.tv_shows.data.local.room.TvShowsDao
import com.bimalghara.tv_shows.data.network.api.TVShowsApi
import com.bimalghara.tv_shows.domain.model.TvShowDetails
import com.bimalghara.tv_shows.domain.model.TvShowsEntity
import com.bimalghara.tv_shows.domain.repository.TVShowsRepositorySource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FakeTVShowsRepositoryImpl @Inject constructor(
    private val tvShowsApi: TVShowsApi,
    private val tvShowsDao: TvShowsDao
) : TVShowsRepositorySource {

    override suspend fun downloadTVShowsList() {
        TODO("Not yet implemented")
    }

    override suspend fun getTVShowDetails(id: Int): TvShowDetails {
        TODO("Not yet implemented")
    }

    override suspend fun getTVShowsList(): Flow<List<TvShowsEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateFavourite(tvShows: TvShowsEntity): Int {
        TODO("Not yet implemented")
    }

    override suspend fun searchTVShowsList(query: String): List<TvShowsEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getSimilarShowsList(id: Int): List<TvShowsEntity> {
        TODO("Not yet implemented")
    }
}