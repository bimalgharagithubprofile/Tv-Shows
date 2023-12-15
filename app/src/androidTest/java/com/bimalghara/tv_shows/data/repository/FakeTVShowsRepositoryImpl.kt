package com.bimalghara.tv_shows.data.repository

import com.bimalghara.tv_shows.data.local.room.TvShowsDao
import com.bimalghara.tv_shows.data.network.api.TVShowsApi
import com.bimalghara.tv_shows.domain.model.Season
import com.bimalghara.tv_shows.domain.model.TvShowDetails
import com.bimalghara.tv_shows.domain.model.TvShowsEntity
import com.bimalghara.tv_shows.domain.repository.TVShowsRepositorySource
import com.bimalghara.tv_shows.utils.DataStatus
import com.bimalghara.tv_shows.utils.DataUtil
import com.bimalghara.tv_shows.utils.FailureType
import com.bimalghara.tv_shows.utils.TestUtil.dataStatusCloud
import com.bimalghara.tv_shows.utils.TestUtil.dataStatusLocal
import com.bimalghara.tv_shows.utils.TestUtil.failureType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeTVShowsRepositoryImpl @Inject constructor(
    private val tvShowsApi: TVShowsApi,
    private val tvShowsDao: TvShowsDao
) : TVShowsRepositorySource {

    override suspend fun downloadTVShowsList() {
        when (dataStatusCloud) {
            DataStatus.Success -> {
                val dummyShows = DataUtil.getDummyTvShows("/dummy_data.json")
                tvShowsDao.addTvShows(dummyShows)
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
        return when (dataStatusLocal) {
            DataStatus.Success -> {
                flowOf(
                    listOf(
                        TvShowsEntity(
                            id = 57243,
                            name = "Doctor Who",
                            overview = "The Doctor is a Time Lord",
                            posterPath = "/4edFyasCrkH4MKs6H4mHqlrxA6b.jpg",
                            popularity = 1403.927,
                            isFavourite = true
                        ),
                        TvShowsEntity(
                            id = 239770,
                            name = "Doctor Me",
                            overview = "The Doctor and friends travel",
                            posterPath = "/91wVL2amQouWhbMfvrVeFNrrHmR.jpg",
                            popularity = 177.541,
                            isFavourite = false
                        )
                    )
                )
            }
            else -> {
                flowOf(
                    emptyList()
                )
            }
        }
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