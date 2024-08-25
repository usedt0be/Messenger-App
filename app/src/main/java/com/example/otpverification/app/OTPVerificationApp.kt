package com.example.otpverification.app

import android.app.Application
import com.example.otpverification.core.components.AppComponent
import com.example.otpverification.core.components.DaggerAppComponent
import com.google.firebase.FirebaseApp


class OTPVerificationApp: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        appComponent = buildAppComponent()
    }


    private fun buildAppComponent() : AppComponent {
        return DaggerAppComponent.builder()
            .applicationContext(this)
            .build()

    }
}