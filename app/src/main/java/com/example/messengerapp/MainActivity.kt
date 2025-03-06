package com.example.messengerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.messengerapp.core.app.MessengerApp
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.presentation.navigation.NavigationGraph
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        (applicationContext as MessengerApp).appComponent.inject(this)
        setContent {
            AppTheme {
                NavigationGraph(
                    factory = factory,
                    firebaseAuth = firebaseAuth
                )
            }
        }
    }
}

