package com.example.movie.di

import com.example.movie.data.repo.MovieRepository
import com.example.movie.domain.repo.MovieRepo
import com.example.movie.utils.ConnectivityManagerNetWorkMonitor
import com.example.movie.utils.NetWorkMonitor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Singleton
    @Binds
    internal abstract fun bindsNetworkMonitor(netWorkMonitor: ConnectivityManagerNetWorkMonitor): NetWorkMonitor

    @Singleton
    @Binds
    internal abstract fun provideMovieRepo(repo: MovieRepository): MovieRepo
}