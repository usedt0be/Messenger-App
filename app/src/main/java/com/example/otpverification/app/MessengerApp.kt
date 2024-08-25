package com.example.otpverification.app

import android.app.Application
import com.example.otpverification.core.components.AppComponent
import com.example.otpverification.core.components.DaggerAppComponent

class MessengerApp: Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = buildAppComponent()
    }


    private fun buildAppComponent() : AppComponent {
        return DaggerAppComponent.builder()
            .applicationContext(this)
            .build()

    }
}