package com.example.movie.di.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO

@Module
@InstallIn(SingletonComponent::class)

object DispatchersModule {
    @Provides
    @Dispatcher(MovieDispatchers.IO)
    fun providesIODispatchers(): CoroutineDispatcher = Dispatchers.IO


    @Provides
    @Dispatcher(MovieDispatchers.Default)
    fun provideRuntimeDispatchers(): CoroutineDispatcher = Dispatchers.Default
}
