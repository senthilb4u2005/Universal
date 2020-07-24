package com.ps.universal.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.lifecycle.*
import com.ps.universal.usermanager.PREFERENCE_NAME
import com.ps.universal.usermanager.User
import com.ps.universal.usermanager.UserManager
import com.ps.universal.usermanager.UserMangerImp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(private val app: Application, private val userManager: UserManager) :
    ViewModel() {


    val splashLiveData: LiveData<Event<SplashEvent>>
        get() = mutableSplashLiveData

    private val mutableSplashLiveData = MutableLiveData<Event<SplashEvent>>()

    val signInLiveData: LiveData<Event<SignInEvent>>
        get() = mutableSignInLiveData

    private val mutableSignInLiveData = MutableLiveData<Event<SignInEvent>>()


    val signUpLiveData: LiveData<Event<SignUpEvent>>
        get() = mutableSignUpLiveData

    private val mutableSignUpLiveData = MutableLiveData<Event<SignUpEvent>>()

    fun splashLaunched() {


        viewModelScope.launch {
            delay(2000)
            mutableSplashLiveData.value = Event(SplashEvent.DoSignIn)
//            if (isUserLoggedIn()) {
//                mutableSplashLiveData.value = Event(SplashEvent.DoSignIn)
//
//            } else {
//                mutableSplashLiveData.value = Event(SplashEvent.DoSignUp)
//            }
        }
    }


    private fun isUserLoggedIn(): Boolean = userManager.isUserLoggedIn()


    fun doSignUp(
        fullName: String,
        email: String,
        mobileNumber: String,
        location: String,
        password: String,
        confirmPassword: String
    ) {

        // have to implement with real API. This is only for testing.
        if (password == "Password") {
            userManager.setUser(User(fullName, email, mobileNumber, location))
            mutableSignUpLiveData.value = Event(SignUpEvent.SignUpSuccess)
        } else {
            mutableSignUpLiveData.value = Event(SignUpEvent.SignUpFailed("Invalid credentials"))
        }

    }

    fun doSignIn(email: String, password: String) {

        // Will do async API call to verify the credentials
        if (userManager.getEmail() == email && password == "Password") {
            mutableSignInLiveData.value = Event(SignInEvent.SignInSuccess)
        } else {
            mutableSignInLiveData.value = Event(SignInEvent.SignInFailed("Invalid credentials"))
        }
    }

    fun removeRegisteredUser() {
        userManager.clearUserData()
    }

    fun checkBiometricAuthentication() {
        if(isBiometricAuthenticationAvailable){
            mutableSignInLiveData.value = Event(SignInEvent.BiometricAuthentication)
        }

    }


    private val isBiometricAuthenticationAvailable: Boolean
        get() {
            val authOptions = BiometricManager.from(app).canAuthenticate()
            return authOptions == BiometricManager.BIOMETRIC_SUCCESS
        }


}


sealed class SplashEvent {

    object DoSignIn : SplashEvent()
    object DoSignUp : SplashEvent()
}

sealed class SignInEvent {
    object BiometricAuthentication : SignInEvent()
    data class SignInFailed(val error: String) : SignInEvent()
    object SignInSuccess : SignInEvent()
}

sealed class SignUpEvent {
    object SignUpSuccess : SignUpEvent()
    object ShowDashboardActivity : SignUpEvent()
    data class SignUpFailed(val error: String) : SignUpEvent()

}


@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(val app: Application) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = LoginViewModel(
        app,
        UserMangerImp(app.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE))
    ) as T
}


