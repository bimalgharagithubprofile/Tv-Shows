package com.bimalghara.tv_shows.domain.use_cases

import com.bimalghara.tv_shows.domain.model.TvShows
import com.bimalghara.tv_shows.domain.repository.TVShowsRepositorySource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchTVShowsUseCase @Inject constructor(private val tvShowsRepositorySource: TVShowsRepositorySource) {

    suspend operator fun invoke(): Flow<List<TvShows>> = flow {
        try {
            val result = tvShowsRepositorySource.getTVShowsList()
            emit(result)
        } catch (e: Exception) {
            throw e
        }
    }

}