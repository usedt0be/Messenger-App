package com.example.messengerapp.presentation.di

import com.example.messengerapp.data.repository.UserStorageRepositoryImpl
import com.example.messengerapp.domain.repository.UserStorageRepository
import dagger.Binds
import dagger.Module

@Module
interface UserStorageModule {
    @Binds
    fun bindUserStorageRepository(
        userStorageRepositoryImpl: UserStorageRepositoryImpl
    ): UserStorageRepository


}