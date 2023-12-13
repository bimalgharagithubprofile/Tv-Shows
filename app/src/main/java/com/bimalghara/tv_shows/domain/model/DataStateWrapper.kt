package com.bimalghara.tv_shows.domain.model

sealed class DataStateWrapper<T>(
    val data: T? = null,
    val errorMsg: String? = null
) {

    class Idle<T> : DataStateWrapper<T>()

    class Loading<T> : DataStateWrapper<T>()

    class Success<T>(data: T?) : DataStateWrapper<T>(data = data)

    class Error<T>(errorMsg: String?) : DataStateWrapper<T>(errorMsg = errorMsg)
}
