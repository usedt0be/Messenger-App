package com.example.messengerapp.core.network.di

import dagger.Module
import dagger.Provides
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
@Singleton
object NetworkModule {

    @Provides
    fun provideJson() : Json {
        return Json {
            ignoreUnknownKeys = true
            encodeDefaults = true

        }
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Timber.tag("OkHttp")
            Timber.d(message = message)
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(json: Json, httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application.json, charset=UTF8".toMediaType()))
            .client(httpClient)
            .baseUrl("$BASE_URL/")
            .build()
    }
}