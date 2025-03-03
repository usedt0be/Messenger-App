package com.example.messengerapp.core.network.interceptor

import android.util.Log
import com.example.messengerapp.core.storage.token.TokensPersistence
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

private const val ACCESS_TOKEN_HEADER = "Authorization"

internal class AuthHeaderInterceptor @Inject constructor(
    private val tokensPersistence: TokensPersistence
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val token = runBlocking { tokensPersistence.getToken().firstOrNull() }
        Log.d("AuthToken_INTERCEPTOR", "$token")

        val newRequest = request.newBuilder()
            .run {
                removeHeader(name = ACCESS_TOKEN_HEADER)
                addHeader(name = ACCESS_TOKEN_HEADER, value = "Bearer ${token?.value}")
            }.build()

        return chain.proceed(newRequest)
    }
}