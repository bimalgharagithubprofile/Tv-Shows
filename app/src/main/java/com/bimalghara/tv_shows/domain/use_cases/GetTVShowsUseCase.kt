package com.bimalghara.tv_shows.domain.use_cases

import com.bimalghara.tv_shows.domain.model.TvShowsEntity
import com.bimalghara.tv_shows.domain.repository.TVShowsRepositorySource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTVShowsUseCase @Inject constructor(private val tvShowsRepositorySource: TVShowsRepositorySource) {

    suspend operator fun invoke(): Flow<List<TvShowsEntity>> {
        return tvShowsRepositorySource.getTVShowsList()
    }

}