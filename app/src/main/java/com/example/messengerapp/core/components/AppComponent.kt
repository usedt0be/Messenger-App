package com.example.messengerapp.core.components

import android.content.Context
import com.example.messengerapp.presentation.di.AuthModule
import com.example.messengerapp.presentation.di.ViewModelModule
import com.example.messengerapp.MainActivity
import com.example.messengerapp.core.annotations.ApplicationContext
import com.example.messengerapp.core.di.ViewModelFactoryModule
import com.example.messengerapp.presentation.di.FirestoreModule
import com.example.messengerapp.presentation.di.StorageModule
import dagger.BindsInstance
import dagger.Component

@Component(modules = [
    AuthModule::class,
    FirestoreModule::class ,
    StorageModule::class,
    ViewModelFactoryModule::class,
    ViewModelModule::class,
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