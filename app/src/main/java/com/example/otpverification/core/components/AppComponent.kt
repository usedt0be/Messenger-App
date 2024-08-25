package com.example.otpverification.core.components

import android.content.Context
import com.example.otpverification.MainActivity
import com.example.otpverification.core.annotations.ApplicationContext
import dagger.BindsInstance
import dagger.Component

@Component(modules = [])
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