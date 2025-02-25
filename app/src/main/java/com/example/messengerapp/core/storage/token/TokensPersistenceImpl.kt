package com.example.messengerapp.core.storage.token

import androidx.compose.ui.input.key.Key
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.messengerapp.core.entity.AuthToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val TOKEN_KEY = stringPreferencesKey(name = "auth_token")

class TokensPersistenceImpl @Inject constructor(
    private val dataStorePrefs: DataStore<Preferences>
): TokensPersistence {

//
//    override val token: Flow<AuthToken?>
//        get() = flow {
//            val token = dataStorePrefs.data.first()[TOKEN_KEY]
//            token?.let {
//                emit(AuthToken(value = token))
//            }
//        }


    override fun saveToken(token: AuthToken): Flow<Unit> {
        return flow {
            dataStorePrefs.edit {
                it[TOKEN_KEY] = token.value
            }
            emit(Unit)
        }
    }

    override fun deleteToken(token: AuthToken): Flow<Unit> = flow {
        dataStorePrefs.edit {
            it.remove(TOKEN_KEY)
        }
        emit(Unit)
    }

    override fun getToken(): Flow<AuthToken> {
        val token = dataStorePrefs.data.map { prefs ->
            prefs[TOKEN_KEY]?.let {
                AuthToken(value = it)
            }
        }
        return  flow {
            token.first()
        }
    }




}
