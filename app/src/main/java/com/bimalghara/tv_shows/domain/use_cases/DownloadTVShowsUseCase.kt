package com.bimalghara.tv_shows.domain.use_cases

import com.bimalghara.tv_shows.domain.repository.TVShowsRepositorySource
import javax.inject.Inject

class DownloadTVShowsUseCase @Inject constructor(private val tvShowsRepositorySource: TVShowsRepositorySource) {

    suspend operator fun invoke() {
        try {
            tvShowsRepositorySource.downloadTVShowsList()
        } catch (e: Exception) {
            throw e
        }
    }

}