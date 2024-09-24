package com.example.messengerapp.presentation.di

import com.example.messengerapp.data.repository.StorageRepositoryImpl
import com.example.messengerapp.domain.StorageRepository
import com.google.firebase.storage.FirebaseStorage
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface StorageModule {

    @Binds
    fun bindStorageRepositoryImpl(storageRepositoryImpl: StorageRepositoryImpl): StorageRepository

    companion object {
        @Provides
        fun provideFirebaseStorage(): FirebaseStorage {
            return FirebaseStorage.getInstance()
        }
    }

}