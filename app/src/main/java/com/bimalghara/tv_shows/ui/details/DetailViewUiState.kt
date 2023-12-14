package com.bimalghara.tv_shows.ui.details

import com.bimalghara.tv_shows.domain.model.TvShowsEntity

data class DetailViewUiState(
    var show: TvShowsEntity? = null,
) {

    companion object {
        const val ARG_SHOW = "arg_show"
    }
}



