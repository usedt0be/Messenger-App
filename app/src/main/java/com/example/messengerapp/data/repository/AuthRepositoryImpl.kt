package com.example.messengerapp.data.repository

import android.app.Activity
import android.util.Log
import com.example.messengerapp.data.entity.AuthData
import com.example.messengerapp.data.entity.UserEntity
import com.example.messengerapp.domain.AuthRepository
import com.example.messengerapp.util.ResultState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): AuthRepository {

    private lateinit var  onVerificationCode: String

    init {
        Log.d("VERIFY_authREPO", "AuthRepository created")
        Log.d("VERIFY_already_authorized", "${firebaseAuth.currentUser != null}")
    }

    override fun verifyPhoneNumberWithOtp(phoneNumber: String, activity: Activity): Flow<ResultState<String>> {
        return callbackFlow {
            val onVerificationCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {}

                override fun onVerificationFailed(exception: FirebaseException) {
                    trySend(ResultState.Error(exception.message))
                }

                override fun onCodeSent(verificationCode: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(verificationCode, p1)
                    trySend(ResultState.Success("Otp sent successfully"))
                    onVerificationCode = verificationCode
                }
            }
            trySend(ResultState.Loading())

            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber("+$phoneNumber")
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(onVerificationCallback)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)

            awaitClose {
                close()
            }
        }
    }

    override fun signWithCredential(otp: String): Flow<ResultState<String>> {
        return callbackFlow {
            trySend(ResultState.Loading())
            val credential = PhoneAuthProvider.getCredential(onVerificationCode, otp)
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        trySend((ResultState.Success(data = "otp verified")))
                    }
                    close()
                }
                .addOnFailureListener{  exception ->
                    trySend(ResultState.Error(exception.message))
                    close(exception)
                }
            awaitClose {
                close()
            }
        }
    }

    override fun checkUserExists(phoneNumber: String): Flow<Boolean> {
        return flow {
            val querySnapshot = firestore.collection("users")
                .whereEqualTo("phoneNumber", phoneNumber)
                .get()
                .await()
            emit(!querySnapshot.isEmpty)
        }.flowOn(Dispatchers.IO)

    }

    override fun getCurrentUser(phoneNumber: String): Flow<ResultState<UserEntity>> = callbackFlow{
        trySend(ResultState.Loading())
        Log.d("user_phoneNumber", phoneNumber)
        firestore.collection("users")
            .whereEqualTo("phoneNumber", phoneNumber)
            .get()
            .addOnSuccessListener {
                val user = it.documents.first().toObject(UserEntity::class.java)
                if(user!=null) {
                    trySend(ResultState.Success(user))
                }
            }
            .addOnFailureListener{
                trySend(ResultState.Error(it.message))
            }
        awaitClose {
            close()
        }
    }

    override suspend fun getAuthData(): AuthData {
        val uid = firebaseAuth.currentUser?.uid!!
        val phoneNumber = firebaseAuth.currentUser?.phoneNumber!!
        return AuthData(uid = uid, phoneNumber = phoneNumber)
    }

    override fun logOut(): Flow<ResultState<String>> {
       return callbackFlow {
           firebaseAuth.signOut()
           trySend(ResultState.Success("Signed Out"))
           awaitClose {
               close()
           }
       }
    }


}