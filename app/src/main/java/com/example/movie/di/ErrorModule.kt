package com.example.movie.di

import com.example.movie.base.ErrorQueue
import com.example.movie.di.coroutine.ApplicationScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ErrorModule {

    @Singleton
    @Provides
    fun provideErrorQueue(@ApplicationScope scope: CoroutineScope): ErrorQueue {
        return ErrorQueue(scope)
    }
}