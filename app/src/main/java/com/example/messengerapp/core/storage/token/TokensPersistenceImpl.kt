package com.example.messengerapp.core.storage.token

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.messengerapp.core.entity.AuthToken
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private val TOKEN_KEY = stringPreferencesKey(name = "auth_token")

class TokensPersistenceImpl @Inject constructor(
    private val dataStorePrefs: DataStore<Preferences>,
    private val firebaseAuth: FirebaseAuth,
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

    override fun getToken(): Flow<AuthToken?> = dataStorePrefs.data.map { token ->
        token[TOKEN_KEY]?.let {
            AuthToken(value = it)
        }
    }

    override suspend fun getFreshToken(): Flow<AuthToken> {
        val token = firebaseAuth.currentUser!!.getIdToken(true).await().token!!
        Log.d("TOKENS_PERSISTENCE", "$token")
        dataStorePrefs.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
        return flow {
            emit(AuthToken(value = token))
        }
    }
}
