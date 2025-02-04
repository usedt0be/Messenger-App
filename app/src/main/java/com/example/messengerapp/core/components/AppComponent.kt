package com.example.messengerapp.core.components

import android.content.Context
import com.example.messengerapp.presentation.di.AuthModule
import com.example.messengerapp.presentation.di.ViewModelModule
import com.example.messengerapp.MainActivity
import com.example.messengerapp.core.annotations.ApplicationContext
import com.example.messengerapp.core.firebase.FirebaseModule
import com.example.messengerapp.core.storage.StorageModule
import com.example.messengerapp.core.viewmodel.di.ViewModelFactoryModule
import com.example.messengerapp.presentation.di.ContactsModule
import com.example.messengerapp.presentation.di.RegistrationModule
import dagger.BindsInstance
import dagger.Component

@Component(modules = [
    AuthModule::class,
    RegistrationModule::class,
    ContactsModule::class,
    FirebaseModule::class,
    ViewModelFactoryModule::class,
    ViewModelModule::class,
    StorageModule::class,
])
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationContext(
            @ApplicationContext context: Context
        ): Builder

        fun build(): AppComponent
    }
    fun inject(activity: MainActivity)
}