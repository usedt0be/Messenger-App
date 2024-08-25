package com.example.otpverification.util

sealed class ResultState<out T>(val data: T? = null) {
    class Success<T>(data: T) : ResultState<T>(data)

    class Error<T>(message: Throwable, data: T? = null) : ResultState<T>(data)


    class Loading<T>(data: T? = null) : ResultState<T>(data)

}




