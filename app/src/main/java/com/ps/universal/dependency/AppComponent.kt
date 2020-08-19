package com.ps.universal.dependency

import android.content.Context
import com.ps.universal.view.registration.RegistrationActivity
import com.ps.universal.view.login.SignInFragment
import com.ps.universal.view.registration.SignUpFragment
import com.ps.universal.view.splash.SplashActivity
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

    fun inject(activity: RegistrationActivity)
    fun inject(activity: SplashActivity)
    fun inject(fragment: SignInFragment)
    fun inject(fragment: SignUpFragment)
}