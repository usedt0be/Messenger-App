package com.example.messengerapp.presentation.di

import androidx.lifecycle.ViewModel
import com.example.messengerapp.presentation.viewmodel.AuthViewModel
import com.example.messengerapp.core.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun provideAuthViewModel(authViewModel: AuthViewModel): ViewModel
}