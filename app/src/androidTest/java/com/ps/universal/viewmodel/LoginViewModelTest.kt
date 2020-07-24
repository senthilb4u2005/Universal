package com.ps.universal.viewmodel

import org.junit.Test
import android.app.Application
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ps.universal.usermanager.UserMangerImp
import io.mockk.mockk
import org.junit.*

class LoginViewModelTest {


    lateinit var loginViewModel: LoginViewModel
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    val sharedPreferences = mockk<SharedPreferences>()



    @Before
    fun setUp() {
        val application = mockk<Application>(relaxed = true)
        loginViewModel = LoginViewModel(application, UserMangerImp(sharedPreferences) )
    }

    @After
    fun tearDown() = Unit

    @Test
    fun splashLaunched() {
        var result: Boolean? = false
        loginViewModel.splashLiveData.observeForever(Observer {
            result = it.getValueIfNotHandled()
        })
        loginViewModel.splashLaunched()
        assert(result == true)
        Assert.assertEquals(null, loginViewModel.splashLiveData.value?.getValueIfNotHandled())
        assert(false == loginViewModel.splashLiveData.value?.peekContent())
    }
}
