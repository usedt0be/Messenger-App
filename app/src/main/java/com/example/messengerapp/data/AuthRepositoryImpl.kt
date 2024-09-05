package com.example.messengerapp.data

import android.app.Activity
import android.util.Log
import com.example.messengerapp.domain.AuthRepository
import com.example.messengerapp.util.ResultState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): AuthRepository {

    private lateinit var  onVerificationCode: String

    init {
        Log.d("VERIFY_authREPO", "AuthRepository created")
    }


    override fun registerUserWithPhoneNumber(phoneNumber: String, activity: Activity): Flow<ResultState<String>> {
        return callbackFlow {

            val onVerificationCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {}

                override fun onVerificationFailed(exception: FirebaseException) {
                    trySend(ResultState.Error(exception))
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
                        trySend((ResultState.Success("otp verified")))
                    }
                    close()
                }
                .addOnFailureListener{  exception ->
                    trySend(ResultState.Error(exception))
                    close(exception)
                }
            awaitClose {
                close()
            }
        }
    }

    override fun getCurrentUserId(): Flow<String> {

        return flow{
            emit(firebaseAuth.currentUser?.uid!!)
        }
    }


}