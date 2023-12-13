package com.bimalghara.tv_shows.ui.details

import com.bimalghara.tv_shows.domain.model.TvShows

data class DetailViewUiState(
    val show: TvShows? = null,
) {

    companion object {
        const val ARG_SHOW = "arg_show"
    }
}



