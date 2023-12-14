package com.bimalghara.tv_shows.di

import android.app.Application
import androidx.room.Room
import com.bimalghara.tv_shows.data.local.room.AppDatabase
import com.bimalghara.tv_shows.data.network.api.TVShowsApi
import com.bimalghara.tv_shows.data.repository.TVShowsRepositoryImpl
import com.bimalghara.tv_shows.domain.repository.TVShowsRepositorySource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun providesTVShowsRepository(tvShowsApi: TVShowsApi, db: AppDatabase): TVShowsRepositorySource {
        return TVShowsRepositoryImpl(tvShowsApi, db.tvShowsDao)
    }
}