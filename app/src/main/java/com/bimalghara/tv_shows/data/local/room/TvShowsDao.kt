package com.bimalghara.tv_shows.data.local.room

import androidx.room.*
import com.bimalghara.tv_shows.domain.model.TvShowsEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TvShowsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTvShows(shows: List<TvShowsEntity>): List<Long>

    //can't be suspending because it's Flow
    @Query("SELECT * FROM TvShowsEntity ORDER BY popularity DESC")
    fun getTVShows(): Flow<List<TvShowsEntity>>

    @Update
    suspend fun updateTvShow(show: TvShowsEntity): Int

}