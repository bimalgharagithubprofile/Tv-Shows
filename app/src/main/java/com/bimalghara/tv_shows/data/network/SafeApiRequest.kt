package com.bimalghara.tv_shows.data.network

import retrofit2.HttpException
import java.net.SocketTimeoutException


abstract class SafeApiRequest {

    //returns DTO
    suspend fun<T> apiRequest(call: suspend () -> T): T {
        return try{
            call.invoke()
        }catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    throw Exception(throwable.code().toString())
                }
                is SocketTimeoutException -> {
                    throw Exception("Connection timeout!")
                }
                else -> {
                    throw Exception("Network error, couldn't get data. Please try again!")
                }
            }
        }
    }
}