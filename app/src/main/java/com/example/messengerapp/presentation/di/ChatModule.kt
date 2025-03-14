package com.example.messengerapp.presentation.di

import com.example.messengerapp.data.repository.ChatRepositoryImpl
import com.example.messengerapp.domain.repository.ChatRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
interface ChatModule {
    @Binds
    @Singleton
    fun bindChatRepository(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository
}