package com.example.messengerapp.core.storage.token

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.messengerapp.core.entity.AuthToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val TOKEN_KEY = stringPreferencesKey(name = "auth_token")

class TokensPersistenceImpl @Inject constructor(
    private val dataStorePrefs: DataStore<Preferences>
) : TokensPersistence {


    override fun saveToken(token: AuthToken): Flow<Unit> {
        return flow {
            dataStorePrefs.edit {
                it[TOKEN_KEY] = token.value
            }
            emit(Unit)
        }
    }

    override suspend fun deleteToken() {
        dataStorePrefs.edit {
            it.clear()
        }
    }

    override fun getToken(): Flow<AuthToken> {
        val token = dataStorePrefs.data.map { prefs ->
            prefs[TOKEN_KEY]?.let {
                AuthToken(value = it)
            }
        }
        return flow {
            token.first()
        }
    }


}
