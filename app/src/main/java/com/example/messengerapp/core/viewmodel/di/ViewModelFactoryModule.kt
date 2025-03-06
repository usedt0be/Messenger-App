package com.example.messengerapp.core.viewmodel.di

import androidx.lifecycle.ViewModelProvider
import com.example.messengerapp.core.viewmodel.factory.DefaultDaggerViewModelFactory
import com.example.messengerapp.core.viewmodel.factory.StateHandleViewModelFactoryImpl
import com.example.messengerapp.core.viewmodel.factory.StateHandleViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindsViewModelFactory(factory: DefaultDaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun bindsSavedStateHandleViewModelFactory
                (factory: StateHandleViewModelFactoryImpl): StateHandleViewModelFactory
}