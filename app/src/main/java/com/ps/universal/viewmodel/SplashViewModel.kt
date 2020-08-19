package com.ps.universal.viewmodel

import androidx.lifecycle.*
import com.ps.universal.usermanager.UserManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val userManager: UserManager) : ViewModel() {

    val splashLiveData: LiveData<Event<SplashEvent>>
        get() = mutableSplashLiveData

    private val mutableSplashLiveData =
        MutableLiveData<Event<SplashEvent>>()

    fun splashLaunched() {
        viewModelScope.launch {
            delay(2000)
            if (userManager.isUserLoggedIn()) {
                mutableSplashLiveData.value = Event(SplashEvent.DoSignIn)
            } else {
                mutableSplashLiveData.value = Event(SplashEvent.DoSignUp)
            }
        }
    }


}

@Suppress("UNCHECKED_CAST")
class SplashViewModelFactory(private val userManager: UserManager) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        SplashViewModel(userManager) as T
}


sealed class SplashEvent {

    object DoSignIn : SplashEvent()
    object DoSignUp : SplashEvent()
}