package com.ps.universal.usermanager

import android.content.SharedPreferences

interface UserManager {
    fun setUser( user: User)
    fun isUserLoggedIn(): Boolean
    fun getUserName(): String?
    fun getEmail(): String?
    fun getLocation(): String?
    fun clearUserData()
}