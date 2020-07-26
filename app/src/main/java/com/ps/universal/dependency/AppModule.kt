package com.ps.universal.dependency

import android.content.Context
import android.content.SharedPreferences
import com.ps.universal.UniversalApplication
import com.ps.universal.usermanager.PREFERENCE_NAME
import dagger.Module
import dagger.Provides

@Module
abstract class AppModule {

    @Provides
    fun provideSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }
}