package com.example.messengerapp.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.messengerapp.UserProto
import com.example.messengerapp.core.storage.dao.ContactDao
import com.example.messengerapp.data.mappers.toUser
import com.example.messengerapp.data.mappers.toUserProto
import com.example.messengerapp.domain.models.User
import com.example.messengerapp.domain.repository.UserStorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class UserStorageRepositoryImpl @Inject constructor(
    private val userDataStore: DataStore<UserProto?>,
    private val contactsDao: ContactDao
): UserStorageRepository {


    override suspend fun getUserFromDataStore(): Flow<User?> {
        val data = userDataStore.data.map {
            it.toUser()
        }
        Log.d("user_FROM_DS", "${data.firstOrNull()}")
        return data
    }

    override suspend fun saveUserToDataStore(user: User) {
        Log.d("UseSavedTODataStore", "$user")
        userDataStore.updateData { user.toUserProto() }
    }

    override suspend fun deleteUserFromDataStore() {
        Timber.tag("user_deleteing").d("Invoked")

        Log.d("user_delete", "invoked")
        userDataStore.updateData {
            it?.defaultInstanceForType
        }
    }

    
}