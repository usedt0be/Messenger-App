package com.example.messengerapp.presentation.di

import androidx.lifecycle.ViewModel
import com.example.messengerapp.core.viewmodel.ViewModelKey
import com.example.messengerapp.core.viewmodel.di.ViewModelFactoryModule
import com.example.messengerapp.core.viewmodel.factory.ViewModelAssistedFactory
import com.example.messengerapp.presentation.viewmodel.AuthViewModel
import com.example.messengerapp.presentation.screens.chat_dialog.ChatDialogViewModel
import com.example.messengerapp.presentation.screens.chats.ChatsViewModel
import com.example.messengerapp.presentation.viewmodel.ContactDetailsViewModel
import com.example.messengerapp.presentation.viewmodel.ContactsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(
    includes = [ViewModelFactoryModule::class]
)
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContactsViewModel::class)
    fun bindContactsViewModel(contactsViewModel: ContactsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatDialogViewModel::class)
    fun bindChatViewModelFactory(factory: ChatDialogViewModel.Factory): ViewModelAssistedFactory<*>


    @Binds
    @IntoMap
    @ViewModelKey(ContactDetailsViewModel::class)
    fun bindContactDetailsViewModel(contactDetailsViewModel: ContactDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatsViewModel::class)
    fun bindChatsViewModel(chatsViewModel: ChatsViewModel): ViewModel
}

