package com.example.messengerapp.presentation.di

import com.example.messengerapp.data.repository.AuthRepositoryImpl
import com.example.messengerapp.domain.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface AuthModule {
    @Binds
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    companion object {
        @Provides
        fun providesFirebaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }

    }
}