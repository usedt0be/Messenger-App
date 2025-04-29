package com.example.messengerapp.core.network.interceptor

import android.util.Log
import com.example.messengerapp.core.storage.token.TokensPersistence
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

private const val UNAUTHORIZED = 401

class ServerErrorInterceptor @Inject constructor(
    private val tokensPersistence: TokensPersistence
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        return if (response.code == UNAUTHORIZED) {
            val newToken = runBlocking { tokensPersistence.getFreshToken().firstOrNull() }
            Log.d("Server_ERROR_INTERCEPTOR", "${newToken}")
             val newRequest = request.newBuilder()
                .removeHeader("Authorization")
                .addHeader(name = "Authorization", value = "Bearer $newToken")
                .build()
            Log.d("Server_ERROR_INTERCEPTOR", "Result ${chain.proceed(newRequest)}, ${newRequest}")
            chain.proceed(newRequest)
        } else {
            Log.d("Server_ERROR_INTERCEPTOR", "Else branch invoked")
            chain.proceed(request)
        }
    }
}