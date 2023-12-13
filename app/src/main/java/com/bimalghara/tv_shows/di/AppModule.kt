package com.bimalghara.tv_shows.di

import com.bimalghara.tv_shows.common.dispatcher.DefaultDispatcherProvider
import com.bimalghara.tv_shows.common.dispatcher.DispatcherProviderSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideCoroutineContext(): DispatcherProviderSource {
        return DefaultDispatcherProvider()
    }

}