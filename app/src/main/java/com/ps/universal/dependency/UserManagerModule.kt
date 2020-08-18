package com.ps.universal.dependency

import androidx.lifecycle.ViewModelProvider
import com.ps.universal.usermanager.UserManager
import com.ps.universal.usermanager.UserMangerImp
import com.ps.universal.viewmodel.LoginViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class UserManagerModule {
    @Binds
    abstract fun provideUserManager(userManager: UserMangerImp) : UserManager


    @Binds
    abstract fun provideViewModelFactory(factory: LoginViewModelFactory): ViewModelProvider.Factory
}