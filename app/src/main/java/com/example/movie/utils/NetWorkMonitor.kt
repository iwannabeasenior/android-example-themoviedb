package com.example.movie.utils

import kotlinx.coroutines.flow.Flow

interface NetWorkMonitor {
    val isOnline: Flow<Boolean>
}