package com.bimalghara.tv_shows.utils

sealed class FailureType {
    object Network : FailureType()
    object Timeout : FailureType()
    object Http : FailureType()
}
