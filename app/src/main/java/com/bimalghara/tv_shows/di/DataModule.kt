package com.bimalghara.tv_shows.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.bimalghara.tv_shows.data.local.datastore.DataStoreProvider
import com.bimalghara.tv_shows.data.network.api.TVShowsApi
import com.bimalghara.tv_shows.data.repository.TVShowsRepositoryImpl
import com.bimalghara.tv_shows.domain.repository.TVShowsRepositorySource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStoreProvider {
        return DataStoreProvider(context)
    }


    @Singleton
    @Provides
    fun providesTVShowsRepository(tvShowsApi: TVShowsApi): TVShowsRepositorySource {
        return TVShowsRepositoryImpl(tvShowsApi)
    }
}