package com.example.messengerapp.presentation.di

import com.example.messengerapp.data.repository.ContactsRepositoryImpl
import com.example.messengerapp.domain.ContactsRepository
import dagger.Binds
import dagger.Module

@Module
interface ContactsModule {
    @Binds
    fun bindContactRepository(contactsRepositoryImpl: ContactsRepositoryImpl): ContactsRepository
}