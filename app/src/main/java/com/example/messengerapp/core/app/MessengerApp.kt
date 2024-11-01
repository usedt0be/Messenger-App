package com.example.messengerapp.core.app

import android.app.Application
import com.example.messengerapp.core.components.AppComponent
import com.example.messengerapp.core.components.DaggerAppComponent
import com.google.firebase.FirebaseApp


class MessengerApp: Application() {

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