package com.bimalghara.tv_shows.domain.use_cases

import com.bimalghara.tv_shows.domain.model.DataStateWrapper
import com.bimalghara.tv_shows.domain.model.TvShows
import com.bimalghara.tv_shows.domain.repository.TVShowsRepositorySource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchTVShowsUseCase @Inject constructor(private val tvShowsRepositorySource: TVShowsRepositorySource) {

    suspend operator fun invoke(query: String): Flow<DataStateWrapper<List<TvShows>>> = flow {
        emit(DataStateWrapper.Loading())
        try {
            val result = tvShowsRepositorySource.searchTVShowsList(query)
            val sortedResult = result.sortedByDescending { it.popularity }
            emit(DataStateWrapper.Success(data = sortedResult))
        } catch (e: Exception) {
            emit(DataStateWrapper.Error(errorMsg = e.message))
        }
    }

}