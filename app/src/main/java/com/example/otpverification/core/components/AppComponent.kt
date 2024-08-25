package com.example.otpverification.core.components

import android.content.Context
import com.example.otpverification.presentation.di.AuthModule
import com.example.otpverification.presentation.di.ViewModelModule
import com.example.otpverification.MainActivity
import com.example.otpverification.core.annotations.ApplicationContext
import com.example.otpverification.core.di.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AuthModule::class, ViewModelFactoryModule::class, ViewModelModule::class])
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