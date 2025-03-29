package com.example.movie.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.movie.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    val sessionIdKey = stringPreferencesKey(SESSION_ID_KEY)
    val isNotFirstTimeKey = booleanPreferencesKey(IS_NOT_FIRST_TIME_KEY)

    suspend fun saveSessionId(sessionId: String) {
        context.dataStore.edit { auth ->
            auth[sessionIdKey] = sessionId
        }
    }

    fun getSessionId(): Flow<String?> {
        return context.dataStore.data
            .map { preferences ->
                preferences[sessionIdKey]
            }
    }
    suspend fun deleteSessionId() {
        context.dataStore.edit { preferences ->
            preferences.remove(sessionIdKey)
        }
    }

    fun isNotFirstTime(): Flow<Boolean?> {
        return context.dataStore.data
            .map { preferences ->
                preferences[isNotFirstTimeKey]
            }
            .flowOn(Dispatchers.IO)
    }

    suspend fun saveIsNotFirstTime() {
        context.dataStore.edit { preferences ->
            preferences[isNotFirstTimeKey] = true
        }
    }

    companion object {
        const val SESSION_ID_KEY = "session_id"
        const val IS_NOT_FIRST_TIME_KEY = "is_not_first_time"
    }
}