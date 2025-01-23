package com.example.messengerapp.presentation.di

import com.example.messengerapp.data.repository.RegistrationRepositoryImpl
import com.example.messengerapp.domain.RegistrationRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface RegistrationModule {

    @Binds
    fun providesFirestoreRepositoryImpl(registrationRepositoryImpl: RegistrationRepositoryImpl): RegistrationRepository

//    companion object {
//        @Provides
//        fun providesFirestoreDb(): FirebaseFirestore {
//            return FirebaseFirestore.getInstance()
//        }
//
//        @Provides
//        fun provideFirebaseStorage(): FirebaseStorage {
//            return FirebaseStorage.getInstance()
//        }
//
//    }

}