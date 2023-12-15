package com.bimalghara.tv_shows.data.repository

import com.bimalghara.tv_shows.data.local.room.TvShowsDao
import com.bimalghara.tv_shows.data.network.api.TVShowsApi
import com.bimalghara.tv_shows.domain.model.Season
import com.bimalghara.tv_shows.domain.model.TvShowDetails
import com.bimalghara.tv_shows.domain.model.TvShowsEntity
import com.bimalghara.tv_shows.domain.repository.TVShowsRepositorySource
import com.bimalghara.tv_shows.utils.DataStatus
import com.bimalghara.tv_shows.utils.DataUtil.dummyTvShows
import com.bimalghara.tv_shows.utils.FailureType
import com.bimalghara.tv_shows.utils.TestUtil.dataStatusCloud
import com.bimalghara.tv_shows.utils.TestUtil.dataStatusLocal
import com.bimalghara.tv_shows.utils.TestUtil.failureType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FakeTVShowsRepositoryImpl @Inject constructor(
    private val tvShowsApi: TVShowsApi,
    private val tvShowsDao: TvShowsDao
) : TVShowsRepositorySource {

    override suspend fun downloadTVShowsList() {
        when (dataStatusCloud) {
            DataStatus.Success -> {
                tvShowsDao.addTvShows(dummyTvShows)
            }
            DataStatus.Fail -> {
                when (failureType) {
                    is FailureType.Http -> {
                        throw Exception("401 - unauthorized")
                    }
                    is FailureType.Timeout -> {
                        throw Exception("socket timeout")
                    }
                    is FailureType.Network -> {
                        throw Exception("no internet")
                    }
                }
            }
            else -> Unit
        }
    }

    override suspend fun getTVShowDetails(id: Int): TvShowDetails {
        return TvShowDetails(
            id = 57243,
            seasons = arrayListOf(
                Season(
                    id = 49191,
                    name = "Specials",
                    seasonNumber = 0,
                    episodeCount = 26
                )
            )
        )
    }

    override suspend fun getTVShowsList(): Flow<List<TvShowsEntity>> {
        when (dataStatusLocal) {
            DataStatus.Success -> {
                tvShowsDao.addTvShows(dummyTvShows)
            }
            else -> Unit
        }

        return tvShowsDao.getTVShows()
    }

    override suspend fun updateFavourite(tvShows: TvShowsEntity): Int {
        return 1
    }

    override suspend fun searchTVShowsList(query: String): List<TvShowsEntity> {
        return emptyList()
    }

    override suspend fun getSimilarShowsList(id: Int): List<TvShowsEntity> {
        return emptyList()
    }
}