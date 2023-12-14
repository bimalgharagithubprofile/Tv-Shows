package com.bimalghara.tv_shows.data.repository

import android.util.Log
import com.bimalghara.tv_shows.BuildConfig
import com.bimalghara.tv_shows.data.local.room.TvShowsDao
import com.bimalghara.tv_shows.data.mapper.toDomain
import com.bimalghara.tv_shows.data.network.api.TVShowsApi
import com.bimalghara.tv_shows.domain.model.TvShowsEntity
import com.bimalghara.tv_shows.domain.repository.TVShowsRepositorySource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TVShowsRepositoryImpl @Inject constructor(
    private val tvShowsApi: TVShowsApi,
    private val tvShowsDao: TvShowsDao
) : TVShowsRepositorySource {
    private val logTag = "TVShowsRepositoryImpl"

    override suspend fun downloadTVShowsList() {
        try {
            val response = tvShowsApi.downloadAllTVShowsFromCloud()
            if (response.results.isNotEmpty()) {
                //extract cloud data into limited business logic usage data
                val shows = response.toDomain()
                if (BuildConfig.DEBUG) Log.d(logTag, shows.toString())

                //shows insert into local db

            } else throw Exception("Failed to download TV-Shows from server!")

        } catch (e: Exception) {
            throw e
        }
    }


    override suspend fun getTVShowsList(): Flow<List<TvShowsEntity>> {
        return tvShowsDao.getTVShows()
    }


    override suspend fun searchTVShowsList(query: String): List<TvShowsEntity> {
        return try {
            val response = tvShowsApi.searchAllTVShowsOnCloud(query)
            if (response.results.isNotEmpty()) {
                //extract cloud data into limited business logic usage data
                val shows = response.toDomain()
                if (BuildConfig.DEBUG) Log.d(logTag, shows.toString())
                shows
            } else throw Exception("No TV-Shows available matching - $query")

        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getSimilarShowsList(id: Int): List<TvShowsEntity> {
        return try {
            val response = tvShowsApi.getAllSimilarShowsFromCloud(id)
            if (response.results.isNotEmpty()) {
                //extract cloud data into limited business logic usage data
                val shows = response.toDomain()
                if (BuildConfig.DEBUG) Log.d(logTag, shows.toString())
                shows
            } else throw Exception("No similar TV-Shows available!")

        } catch (e: Exception) {
            throw e
        }
    }


}
