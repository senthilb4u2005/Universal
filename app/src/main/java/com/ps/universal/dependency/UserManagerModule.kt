package com.ps.universal.dependency

import com.ps.universal.usermanager.UserManager
import com.ps.universal.usermanager.UserMangerImp
import dagger.Binds
import dagger.Module

@Module
abstract class UserManagerModule {
    @Binds
    abstract fun provideUserManager(userManager: UserMangerImp) : UserManager
}