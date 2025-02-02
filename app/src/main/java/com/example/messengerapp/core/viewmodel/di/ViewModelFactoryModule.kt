package com.example.messengerapp.core.viewmodel.di

import androidx.lifecycle.ViewModelProvider
import com.example.messengerapp.core.viewmodel.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindsViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}