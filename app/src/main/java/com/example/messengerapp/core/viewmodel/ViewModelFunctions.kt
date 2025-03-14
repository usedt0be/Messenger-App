package com.example.messengerapp.core.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.messengerapp.core.app.MessengerApp


@Composable
inline fun <reified VM : ViewModel> daggerViewModel(): VM {
    val context = LocalContext.current.applicationContext as MessengerApp
    val factory = context.appComponent.getViewModelFactory()

    return viewModel{
        val savedStateHandle = createSavedStateHandle()
        factory.create(VM::class.java, savedStateHandle)
    }

}