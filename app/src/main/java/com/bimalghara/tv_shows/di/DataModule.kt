package com.bimalghara.tv_shows.di

import com.bimalghara.tv_shows.data.network.api.TVShowsApi
import com.bimalghara.tv_shows.data.repository.TVShowsRepositoryImpl
import com.bimalghara.tv_shows.domain.repository.TVShowsRepositorySource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    fun providesTVShowsRepository(tvShowsApi: TVShowsApi): TVShowsRepositorySource {
        return TVShowsRepositoryImpl(tvShowsApi)
    }
}