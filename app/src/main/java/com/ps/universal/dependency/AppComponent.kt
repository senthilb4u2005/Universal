package com.ps.universal.dependency

import android.content.Context
import com.ps.universal.UniversalApplication
import dagger.BindsInstance
import dagger.Component

@Component
interface AppComponent {

    @dagger.Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}