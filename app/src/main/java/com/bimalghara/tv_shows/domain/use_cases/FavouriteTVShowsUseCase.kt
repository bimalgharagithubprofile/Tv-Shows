package com.bimalghara.tv_shows.domain.use_cases

import com.bimalghara.tv_shows.domain.model.TvShowsEntity
import com.bimalghara.tv_shows.domain.repository.TVShowsRepositorySource
import javax.inject.Inject

class FavouriteTVShowsUseCase @Inject constructor(private val tvShowsRepositorySource: TVShowsRepositorySource) {

    suspend operator fun invoke(tvShows: TvShowsEntity, isFavourite: Boolean): TvShowsEntity? {
        val updatedTvShowsEntity =tvShows.apply {
            this.isFavourite = isFavourite
        }
        val result = tvShowsRepositorySource.updateFavourite(updatedTvShowsEntity)
        return if(result > 0){
            updatedTvShowsEntity
        } else null
    }

}