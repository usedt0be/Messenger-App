package com.example.messengerapp.core.viewmodel.factory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

interface ViewModelAssistedFactory<T: ViewModel> {
    fun create(handle: SavedStateHandle): T
}