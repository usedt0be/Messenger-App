package com.example.messengerapp.core.network.di

import android.util.Log
import com.example.messengerapp.core.network.interceptor.AuthHeaderInterceptor
import com.example.messengerapp.core.network.interceptor.ServerErrorInterceptor
import com.example.messengerapp.core.storage.token.TokensPersistence
import com.example.messengerapp.data.network.ChatApiService
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "http://10.0.2.2:8080"
private const val HTTP_CONNECT_TIMEOUT = 60_000L
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson() : Json {
        return Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokensPersistence: TokensPersistence
    ): OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor { message ->
//            Timber.tag("OkHttp")
//            Timber.d(message = message)
            Log.d("OkHttp", "$message")
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(interceptor = AuthHeaderInterceptor(tokensPersistence))
            .addInterceptor(interceptor = ServerErrorInterceptor(tokensPersistence))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(json: Json, httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(httpClient)
            .baseUrl("$BASE_URL/")
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(Logging)
            install(WebSockets)
            install(ContentNegotiation) {
                json(
                    Json{
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
        }
    }

    @Provides
    @Singleton
    fun bindChatApiService(retrofit: Retrofit):ChatApiService {
        return retrofit.create(ChatApiService::class.java)
    }
}