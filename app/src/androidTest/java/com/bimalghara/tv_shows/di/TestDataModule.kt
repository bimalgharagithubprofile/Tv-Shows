package com.bimalghara.tv_shows.di

import com.bimalghara.tv_shows.data.local.room.AppDatabase
import com.bimalghara.tv_shows.data.network.api.TVShowsApi
import com.bimalghara.tv_shows.data.repository.FakeTVShowsRepositoryImpl
import com.bimalghara.tv_shows.domain.repository.TVShowsRepositorySource
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
@Module
object TestDataModule {

    @Provides
    fun providesFakeTVShowsRepository(tvShowsApi: TVShowsApi, db: AppDatabase): TVShowsRepositorySource {
        return FakeTVShowsRepositoryImpl(tvShowsApi, db.tvShowsDao)
    }
}