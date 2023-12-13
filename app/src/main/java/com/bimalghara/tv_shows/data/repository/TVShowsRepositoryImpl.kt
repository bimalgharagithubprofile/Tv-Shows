package com.bimalghara.tv_shows.data.repository

import android.util.Log
import com.bimalghara.tv_shows.BuildConfig
import com.bimalghara.tv_shows.data.mapper.toDomain
import com.bimalghara.tv_shows.data.network.api.TVShowsApi
import com.bimalghara.tv_shows.domain.model.TvShows
import com.bimalghara.tv_shows.domain.repository.TVShowsRepositorySource
import javax.inject.Inject

class TVShowsRepositoryImpl @Inject constructor(private val tvShowsApi: TVShowsApi) : TVShowsRepositorySource {
    private val logTag = "TVShowsRepositoryImpl"

    override suspend fun getTVShowsList(): List<TvShows> {
        return try {
            val response = tvShowsApi.getAllTVShows()
            if (response.results.isNotEmpty()) {
                //extract cloud data into limited business logic usage data
                val breeds = response.toDomain()
                if(BuildConfig.DEBUG) Log.d(logTag, breeds.toString())
                breeds
            } else throw Exception("No TV-Shows available at this moment!")

        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getSimilarShowsList(id: Int): List<TvShows> {
        return try {
            val response = tvShowsApi.getAllSimilarShows(id)
            if (response.results.isNotEmpty()) {
                //extract cloud data into limited business logic usage data
                val breeds = response.toDomain()
                if(BuildConfig.DEBUG) Log.d(logTag, breeds.toString())
                breeds
            } else throw Exception("No similar TV-Shows available!")

        } catch (e: Exception) {
            throw e
        }
    }


}
