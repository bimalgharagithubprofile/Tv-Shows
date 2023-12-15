package com.bimalghara.tv_shows.domain.use_cases

import com.bimalghara.tv_shows.domain.model.DataStateWrapper
import com.bimalghara.tv_shows.domain.model.TvShowDetails
import com.bimalghara.tv_shows.domain.repository.TVShowsRepositorySource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchTvShowDetailsUseCase @Inject constructor(private val tvShowsRepositorySource: TVShowsRepositorySource) {

    suspend operator fun invoke(id:Int): Flow<DataStateWrapper<TvShowDetails>> = flow {
        emit(DataStateWrapper.Loading())
        try {
            val result = tvShowsRepositorySource.getTVShowDetails(id)
            emit(DataStateWrapper.Success(data = result))
        } catch (e: Exception) {
            emit(DataStateWrapper.Error(errorMsg = e.message))
        }
    }

}