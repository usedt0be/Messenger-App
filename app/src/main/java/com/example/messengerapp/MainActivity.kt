package com.example.messengerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.messengerapp.core.app.MessengerApp
import com.example.messengerapp.core.theme.AppTheme
import com.example.messengerapp.presentation.navigation.NavigationGraph
import com.example.messengerapp.ui.theme.MessengerTheme
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as MessengerApp).appComponent.inject(this)
        setContent {
            AppTheme{
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationGraph(
                        activity = this ,
                        factory = factory,
                        firebaseAuth = firebaseAuth
                    )
                }
            }
        }
    }
}

