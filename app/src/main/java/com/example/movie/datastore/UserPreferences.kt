package com.example.movie.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.movie.dataStore
import com.example.movie.di.coroutine.ApplicationScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context,
    @ApplicationScope private val scope: CoroutineScope
) {

    val sessionIdKey = stringPreferencesKey(SESSION_ID_KEY)
    val isNotFirstTimeKey = booleanPreferencesKey(IS_NOT_FIRST_TIME_KEY)
    val accessTokenKey = stringPreferencesKey(ACCESS_TOKEN)
    val accountIdKey = stringPreferencesKey(ACCOUNT_ID)

    var accessToken: String? = null

    suspend fun startGetAccessToken() {
        context.dataStore.data.map { preferences ->
            preferences[accessTokenKey]
        }.collectLatest {
            accessToken = it
        }
    }
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
        return context.dataStore.data // read data here
            .map { preferences ->
                preferences[isNotFirstTimeKey]
            }
    }

    suspend fun saveIsNotFirstTime() {
        context.dataStore.edit { preferences ->
            preferences[isNotFirstTimeKey] = true
        }
    }


    suspend fun saveAccessToken(accessToken: String) {
        context.dataStore.edit { preferences ->
            preferences[accessTokenKey] = accessToken
        }
    }


    suspend fun removeAccessToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(accessTokenKey)
        }
    }

    fun getAccessToken(): Flow<String?> {
        return context.dataStore.data
            .map { preferences -> preferences[accessTokenKey] }
            .flowOn(Dispatchers.IO)
    }

    fun getAccountId(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[accountIdKey]
        }
    }

    suspend fun removeAccountId() {
        context.dataStore.edit { preferences ->
            preferences.remove(accountIdKey)
        }
    }

    suspend fun saveAccountId(accountId: String) {
        context.dataStore.edit { preferences ->
            preferences[accountIdKey] = accountId
        }
    }
    companion object {
        const val SESSION_ID_KEY = "session_id"
        const val IS_NOT_FIRST_TIME_KEY = "is_not_first_time"
        const val ACCESS_TOKEN = "access_token"
        const val ACCOUNT_ID = "account_id"
    }
}