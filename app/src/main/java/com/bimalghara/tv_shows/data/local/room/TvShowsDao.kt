package com.bimalghara.tv_shows.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bimalghara.tv_shows.domain.model.TvShowsEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TvShowsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTvShows(shows: List<TvShowsEntity>): List<Long>

    //can't be suspending because it's Flow
    @Query("SELECT * FROM TvShowsEntity ORDER BY popularity DESC")
    fun getTVShows(): Flow<List<TvShowsEntity>>

    @Query("DELETE FROM TvShowsEntity")
    suspend fun truncate()

}