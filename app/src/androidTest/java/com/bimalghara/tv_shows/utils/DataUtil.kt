package com.bimalghara.tv_shows.utils

import com.bimalghara.tv_shows.domain.model.TvShowsEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

object DataUtil {
    fun getDummyTvShows(fileName: String): List<TvShowsEntity> {
        val inputStream = DataUtil::class.java.getResourceAsStream(fileName)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        val jsonString = builder.toString()
        val tvShowsEntityType = object : TypeToken<List<TvShowsEntity>>() {}.type
        return Gson().fromJson(jsonString, tvShowsEntityType)
    }
}