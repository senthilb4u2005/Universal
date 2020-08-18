package com.ps.universal.viewmodel

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.biometric.BiometricManager
import androidx.lifecycle.*
import com.ps.universal.R
import com.ps.universal.usermanager.PREFERENCE_NAME
import com.ps.universal.usermanager.User
import com.ps.universal.usermanager.UserManager
import com.ps.universal.usermanager.UserMangerImp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

open class RegistrationViewModel(private val app: Application, private val userManager: UserManager) :
    ViewModel() {

    val splashLiveData: LiveData<Event<SplashEvent>>
        get() = mutableSplashLiveData

    internal val mutableSplashLiveData = MutableLiveData<Event<SplashEvent>>()

    val signInLiveData: LiveData<Event<SignInEvent>>
        get() = mutableSignInLiveData

    private val mutableSignInLiveData = MutableLiveData<Event<SignInEvent>>()


    val signUpLiveData: LiveData<Event<SignUpEvent>>
        get() = mutableSignUpLiveData

    private val mutableSignUpLiveData = MutableLiveData<Event<SignUpEvent>>()


    val termsAndConditionLiveData: LiveData<Event<TermsAndConditionEvent>>
        get() = mutableTermsAndConditionLiveData

    private val mutableTermsAndConditionLiveData = MutableLiveData<Event<TermsAndConditionEvent>>()

   open fun splashLaunched() {
        viewModelScope.launch {
            delay(2000)
            if (isUserLoggedIn()) {
                mutableSplashLiveData.value = Event(SplashEvent.DoSignIn)
            } else {
                mutableSplashLiveData.value = Event(SplashEvent.DoSignUp)
            }
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
            mutableSignInLiveData.value = Event(SignInEvent.SignInFailed(R.string.login_error))
        }
    }

    fun removeRegisteredUser() {
        userManager.clearUserData()
    }

    fun checkBiometricAuthentication() {
        if (isBiometricAuthenticationAvailable) {
            mutableSignInLiveData.value = Event(SignInEvent.BiometricAuthentication)
        }

    }

    fun onTermsAndConditionDecline() {

        mutableTermsAndConditionLiveData.value = Event(TermsAndConditionEvent.Decline)
        userManager.clearUserData()
    }

    fun onTermsAndConditionAccept() {

        mutableTermsAndConditionLiveData.value = Event(TermsAndConditionEvent.Accept)
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
    data class SignInFailed(@StringRes val error: Int) : SignInEvent()
    object SignInSuccess : SignInEvent()
}

sealed class SignUpEvent {
    object SignUpSuccess : SignUpEvent()
    object ShowDashboardActivity : SignUpEvent()
    data class SignUpFailed(val error: String) : SignUpEvent()

}

sealed class TermsAndConditionEvent {
    object Accept : TermsAndConditionEvent()
    object Decline : TermsAndConditionEvent()
}

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory @Inject constructor(val app: Application) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = RegistrationViewModel(
        app,
        UserMangerImp(app.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE))
    ) as T
}


