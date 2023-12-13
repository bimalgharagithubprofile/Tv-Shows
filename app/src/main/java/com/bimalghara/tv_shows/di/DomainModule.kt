package com.bimalghara.tv_shows.di

import com.bimalghara.tv_shows.domain.repository.TVShowsRepositorySource
import com.bimalghara.tv_shows.domain.use_cases.FetchSimilarShowsUseCase
import com.bimalghara.tv_shows.domain.use_cases.FetchTVShowsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Provides
    fun providesFetchTVShowsUseCase(tvShowsRepositorySource: TVShowsRepositorySource): FetchTVShowsUseCase {
        return FetchTVShowsUseCase(tvShowsRepositorySource)
    }

    @Provides
    fun providesFetchSimilarShowsUseCase(tvShowsRepositorySource: TVShowsRepositorySource): FetchSimilarShowsUseCase {
        return FetchSimilarShowsUseCase(tvShowsRepositorySource)
    }


}