package com.bimalghara.tv_shows.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bimalghara.tv_shows.domain.model.TvShows
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DataStoreProvider @Inject constructor(@ApplicationContext private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "common")

    private val dataStore by lazy {
        context.dataStore
    }


    suspend fun addFavourite(data:TvShows){
        val datSet = getFavourites().toMutableList()
        datSet.add(data)

        dataStore.edit {
            it[KEY_FAVOURITES] = Gson().toJson(datSet)
        }
    }
    suspend fun getFavourites(): List<TvShows> {
        val preferences = dataStore.data.first()
        val data = preferences[KEY_FAVOURITES]

        if(data.isNullOrEmpty())
            return emptyList()

        val listType = object : TypeToken<List<TvShows>>() {}.type
        return  Gson().fromJson(data, listType)
    }



    companion object {
        val KEY_FAVOURITES = stringPreferencesKey("my_favourites")
    }
}