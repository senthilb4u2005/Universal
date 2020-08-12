package com.ps.universal.dependency

import android.content.Context
import com.ps.universal.view.MainActivity
import com.ps.universal.view.login.SignInFragment
import com.ps.universal.view.login.SignUpFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, UserManagerModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: SignInFragment)
    fun inject(fragment: SignUpFragment)
}