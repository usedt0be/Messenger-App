package com.example.messengerapp.presentation.di

import com.example.messengerapp.data.repository.ChatRepositoryImpl
import com.example.messengerapp.domain.repository.ChatRepository
import dagger.Binds
import dagger.Module


@Module
interface ChatModule {
    @Binds
    fun bindChatRepository(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository

}