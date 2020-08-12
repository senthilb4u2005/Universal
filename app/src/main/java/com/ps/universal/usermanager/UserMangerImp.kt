package com.ps.universal.usermanager

import android.content.SharedPreferences
import javax.inject.Inject

const val USER_NAME = "USER_NAME"
const val IS_LOGGED_IN = "IS_LOGGED_IN"
const val USER_EMAIL = "USER_EMAIL"
const val USER_LOCATION = "USER_LOCATION"
const val PREFERENCE_NAME = "UNIVERSAL_PREFERENCE"


class UserMangerImp @Inject constructor(private val preferences: SharedPreferences) : UserManager {

    override fun setUser(user: User) {
        preferences.edit().putString(USER_NAME, user.name)
            .putBoolean(IS_LOGGED_IN, true)
            .putString(USER_EMAIL, user.email)
            .putString(USER_LOCATION, user.location)
            .apply()
    }

    override fun isUserLoggedIn(): Boolean = preferences.getBoolean(IS_LOGGED_IN, false)


    override fun getUserName(): String? = preferences.getString(USER_NAME, null)
    override fun getEmail(): String? = preferences.getString(USER_EMAIL, null)
    override fun getLocation(): String? = preferences.getString(USER_LOCATION, null)
    override fun clearUserData() {
        preferences.edit().clear().apply()
    }

}