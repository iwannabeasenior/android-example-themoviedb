package com.example.movie.di.coroutine

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

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
