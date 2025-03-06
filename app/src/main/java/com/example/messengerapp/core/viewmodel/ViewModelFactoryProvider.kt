package com.example.messengerapp.core.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.messengerapp.core.viewmodel.factory.StateHandleViewModelFactory


@Composable
fun ViewModelFactoryProvider(
    viewModelFactory: StateHandleViewModelFactory,
    content: @Composable () -> Unit
){
    CompositionLocalProvider(
        value = LocalViewModelFactory provides viewModelFactory,
        content = content
    )
}