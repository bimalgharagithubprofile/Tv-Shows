package com.bimalghara.tv_shows.common.dispatcher

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider: DispatcherProviderSource {

    private val testDispatcher = StandardTestDispatcher()

    override val main
        get() = testDispatcher
    override val default
        get() = testDispatcher
    override val io
        get() = testDispatcher
    override val unconfined
        get() = testDispatcher
}