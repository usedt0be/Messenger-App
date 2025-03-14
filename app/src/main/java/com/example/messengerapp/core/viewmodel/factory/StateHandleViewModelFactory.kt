package com.example.messengerapp.core.viewmodel.factory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

interface StateHandleViewModelFactory {
    fun <VM : ViewModel> create(modelClass: Class<VM>, handle: SavedStateHandle): VM
}