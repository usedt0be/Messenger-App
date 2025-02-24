package com.example.messengerapp.core.storage.token

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TokensPersistenceImpl @Inject constructor(
    private val dataStorePrefs: DataStore<Preferences>
): TokensPersistence {

    private val tokenKey = stringPreferencesKey(name = "auth_token")
    override val token: Flow<String?>
        get() = flow {
            val preferences = dataStorePrefs.data.first()
            emit(preferences[tokenKey])
        }


    override fun saveToken(token: String): Flow<Unit> = flow {
        dataStorePrefs.edit {
            it[tokenKey] = token
        }
        emit(Unit)
    }

    override fun deleteToken(token: String): Flow<Unit> = flow{
        dataStorePrefs.edit {
            it.remove(tokenKey)
        }
        emit(Unit)
    }

}
