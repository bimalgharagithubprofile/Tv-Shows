package com.bimalghara.tv_shows.di

import android.app.Application
import androidx.room.Room
import com.bimalghara.tv_shows.data.local.room.AppDatabase
import com.bimalghara.tv_shows.data.network.api.TVShowsApi
import com.bimalghara.tv_shows.data.repository.FakeTVShowsRepositoryImpl
import com.bimalghara.tv_shows.domain.repository.TVShowsRepositorySource
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
@Module
object TestDataModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): AppDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            AppDatabase::class.java,
        ).build()
    }

    @Provides
    fun providesFakeTVShowsRepository(tvShowsApi: TVShowsApi, db: AppDatabase): TVShowsRepositorySource {
        return FakeTVShowsRepositoryImpl(tvShowsApi, db.tvShowsDao)
    }
}