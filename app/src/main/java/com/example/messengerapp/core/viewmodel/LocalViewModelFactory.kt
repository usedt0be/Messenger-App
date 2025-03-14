package com.example.messengerapp.core.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import com.example.messengerapp.core.viewmodel.factory.StateHandleViewModelFactory

object LocalViewModelFactory {

    private val LocalViewModelFactory =
        compositionLocalOf<StateHandleViewModelFactory?> {null}

    val current: StateHandleViewModelFactory?
        @Composable
        get() = LocalViewModelFactory.current


    infix fun provides(viewModelFactory: StateHandleViewModelFactory):
            ProvidedValue<StateHandleViewModelFactory?> {
        return LocalViewModelFactory.provides(viewModelFactory)
    }
}