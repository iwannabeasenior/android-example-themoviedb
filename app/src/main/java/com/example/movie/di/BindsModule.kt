package com.example.movie.di

import com.example.data.repo.MovieRepository
import com.example.data.repo.UserRepository
import com.example.domain.repo.MovieRepo
import com.example.domain.repo.UserRepo
import com.example.movie.utils.ConnectivityManagerNetWorkMonitor
import com.example.movie.utils.NetWorkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {
    @Singleton
    @Binds
    internal abstract fun bindsNetworkMonitor(netWorkMonitor: ConnectivityManagerNetWorkMonitor): NetWorkMonitor

    @Singleton
    @Binds
    internal abstract fun provideMovieRepo(repo: MovieRepository): MovieRepo

//    @Singleton
//    @Binds
//    internal abstract fun provideSearchRepo(repo: SearchRepository): SearchRepo

    @Singleton
    @Binds
    internal abstract fun provideUserRepo(repo: UserRepository): UserRepo
}