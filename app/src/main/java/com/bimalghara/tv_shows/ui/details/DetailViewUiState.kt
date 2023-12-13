package com.bimalghara.tv_shows.ui.details

data class DetailViewUiState(
    val id: Int? = null,
    val name: String? = null,
    val image: String? = null,
) {

    companion object {
        const val ARG_ID = "arg_id"
        const val ARG_NAME = "arg_name"
        const val ARG_IMAGE_URL = "arg_imageUrl"
    }
}



