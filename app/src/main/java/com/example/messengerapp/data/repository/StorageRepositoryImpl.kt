package com.example.messengerapp.data.repository

import android.net.Uri
import android.util.Log
import com.example.messengerapp.domain.StorageRepository
import com.example.messengerapp.util.ResultState
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage
):StorageRepository {
    override fun uploadImage(imageUri: Uri?, userId: String): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading())
        val storageReference = storage.reference.child("users_profile_images/ $userId.jpg ")

        imageUri?.let { uri ->
            Log.d("user_id", "$uri")

            storageReference.putFile(uri)
                .addOnSuccessListener{
                    it.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                        trySend(ResultState.Success(it.toString()))
                    }

                }
                .addOnFailureListener {
                    trySend(ResultState.Error(it))
                }
        }
        awaitClose {
            close()
        }
    }
}