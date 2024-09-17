package com.example.messengerapp.presentation.di

import com.example.messengerapp.data.repository.FirestoreRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides


@Module
object FirestoreModule {
    @Provides
    fun providesFirestoreDb(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun providesFirestoreRepositoryImpl(firestore: FirebaseFirestore): FirestoreRepositoryImpl {
        return FirestoreRepositoryImpl(firestore)
    }
}