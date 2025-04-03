package com.example.messengerapp.util

sealed class ResultState<out T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ResultState<T>(data)

    class Error<T>(message: String?, data: T? = null) : ResultState<T>(data,message)

    class Loading<T>(data: T? = null) : ResultState<T>(data)
}




