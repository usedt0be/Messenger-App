package com.example.messengerapp.presentation.di

import androidx.lifecycle.ViewModel
import com.example.messengerapp.presentation.viewmodel.AuthViewModel
import com.example.messengerapp.core.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun provideAuthViewModel(authViewModel: AuthViewModel): ViewModel
}