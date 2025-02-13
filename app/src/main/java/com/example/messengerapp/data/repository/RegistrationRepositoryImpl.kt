package com.example.messengerapp.data.repository

import android.net.Uri
import android.util.Log
import com.example.messengerapp.data.AuthData
import com.example.messengerapp.data.dto.UserDto
import com.example.messengerapp.domain.repository.RegistrationRepository
import com.example.messengerapp.util.ResultState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val firebaseAuth: FirebaseAuth
): RegistrationRepository {

    override fun insert(user: UserDto): Flow<ResultState<String>> = callbackFlow {
        Log.d("user_insert", "$user")
        firestore.collection("users")
            .add(user)
            .addOnSuccessListener {
                trySend(ResultState.Success("Data is inserted"))
            }
            .addOnFailureListener { exception ->
                trySend(ResultState.Error(exception.message))
            }
        awaitClose {
            close()
        }
    }


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
                .addOnFailureListener { exception ->
                    trySend(ResultState.Error(exception.message))
                }
        }
        awaitClose {
            close()
        }
    }

    override suspend fun getRegistrationAuthData(): AuthData {
        val uid = firebaseAuth.currentUser?.uid!!
        val phoneNumber = firebaseAuth.currentUser?.phoneNumber!!
        return AuthData(uid = uid, phoneNumber = phoneNumber)
    }

    val customscope = CoroutineScope(SupervisorJob())

    suspend fun dd() {
      val vb =  supervisorScope {

      }
     val d =   customscope.launch { this

        }
    }
}