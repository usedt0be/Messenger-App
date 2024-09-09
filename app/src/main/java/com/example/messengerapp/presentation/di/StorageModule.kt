package com.example.messengerapp.presentation.di

import com.example.messengerapp.data.repository.StorageRepositoryImpl
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides


@Module
object StorageModule {
    @Provides
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    fun provideStorageRepositoryImpl(storage: FirebaseStorage): StorageRepositoryImpl {
        return StorageRepositoryImpl(storage)
    }
}