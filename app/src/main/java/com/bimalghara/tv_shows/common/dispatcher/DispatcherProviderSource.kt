package com.bimalghara.tv_shows.common.dispatcher

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface DispatcherProviderSource {
    val main: CoroutineContext
        get() = Dispatchers.Main
    val default: CoroutineContext
        get() = Dispatchers.Default
    val io: CoroutineContext
        get() = Dispatchers.IO
    val unconfined: CoroutineContext
        get() = Dispatchers.Unconfined
}