package com.bimalghara.tv_shows.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bimalghara.tv_shows.domain.model.TvShowsEntity


@Database(
    entities = [TvShowsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "tv_shows_app_db"
    }

    abstract val tvShowsDao: TvShowsDao
}