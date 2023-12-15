package com.bimalghara.tv_shows.utils

sealed class DataStatus {
    object EmptyResponse : DataStatus()
    object Success : DataStatus()
    object Fail : DataStatus()
}