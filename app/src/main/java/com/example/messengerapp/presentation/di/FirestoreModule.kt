package com.example.messengerapp.presentation.di

import com.example.messengerapp.data.repository.FirestoreRepositoryImpl
import com.example.messengerapp.domain.FirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface FirestoreModule {

    @Binds
    fun providesFirestoreRepositoryImpl(firestoreRepositoryImpl: FirestoreRepositoryImpl): FirestoreRepository

    companion object {
        @Provides
        fun providesFirestoreDb(): FirebaseFirestore {
            return FirebaseFirestore.getInstance()
        }
    }

}