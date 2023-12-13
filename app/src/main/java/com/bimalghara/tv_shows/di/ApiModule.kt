package com.bimalghara.tv_shows.di

import com.bimalghara.tv_shows.data.network.api.TVShowsApi
import com.bimalghara.tv_shows.data.network.retrofit.ApiServiceGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    @Singleton
    @Provides
    fun provideTVShowsApi(serviceGenerator: ApiServiceGenerator): TVShowsApi {
        return TVShowsApi(serviceGenerator)
    }
}
