package com.bimalghara.tv_shows.di

import com.bimalghara.tv_shows.domain.repository.TVShowsRepositorySource
import com.bimalghara.tv_shows.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Provides
    fun providesDownloadTVShowsUseCase(tvShowsRepositorySource: TVShowsRepositorySource): DownloadTVShowsUseCase {
        return DownloadTVShowsUseCase(tvShowsRepositorySource)
    }
    @Provides
    fun providesGetTVShowsUseCase(tvShowsRepositorySource: TVShowsRepositorySource): GetTVShowsUseCase {
        return GetTVShowsUseCase(tvShowsRepositorySource)
    }

    @Provides
    fun providesFavouriteTVShowsUseCase(tvShowsRepositorySource: TVShowsRepositorySource): FavouriteTVShowsUseCase {
        return FavouriteTVShowsUseCase(tvShowsRepositorySource)
    }

    @Provides
    fun providesSearchTVShowsUseCase(tvShowsRepositorySource: TVShowsRepositorySource): SearchTVShowsUseCase {
        return SearchTVShowsUseCase(tvShowsRepositorySource)
    }

    @Provides
    fun providesFetchSimilarShowsUseCase(tvShowsRepositorySource: TVShowsRepositorySource): FetchSimilarShowsUseCase {
        return FetchSimilarShowsUseCase(tvShowsRepositorySource)
    }


}