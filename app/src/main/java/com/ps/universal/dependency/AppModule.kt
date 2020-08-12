package com.ps.universal.dependency

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.ps.universal.usermanager.PREFERENCE_NAME
import com.ps.universal.usermanager.UserManager
import com.ps.universal.usermanager.UserMangerImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
 class AppModule {

    @Singleton
    @Provides
    fun provideSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideApplication(context: Context): Application{
        return context as Application
    }




}