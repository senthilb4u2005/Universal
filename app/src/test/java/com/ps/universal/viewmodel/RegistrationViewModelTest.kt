package com.ps.universal.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ps.universal.usermanager.UserMangerImp
import com.ps.universal.utils.MainCoroutineScopeRule
import getOrAwaitValue
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RegistrationViewModelTest {


    lateinit var registrationViewModel: RegistrationViewModel

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private val userManager = mockk<UserMangerImp>()


    @Before
    fun setUp() {
        val application = mockk<Application>(relaxed = true)
        registrationViewModel = RegistrationViewModel(application, userManager)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }


    @Test
    fun splashLaunched_user_not_registered_show_sign_up_screen() {
        runBlocking {
            launch(Dispatchers.Main) {
                every { userManager.isUserLoggedIn() } returns false
                registrationViewModel.splashLiveData.observeForever(Observer {
                })
                registrationViewModel.splashLaunched()
                coroutineScope.advanceTimeBy(2000)
                Assert.assertEquals(
                    SplashEvent.DoSignUp,
                    registrationViewModel.splashLiveData.getOrAwaitValue().getValueIfNotHandled()
                )
            }
        }
    }


    @Test
    fun splashLaunched_user_is_registered_show_sign_in_screen() {
        runBlocking {
            launch(Dispatchers.Main) {

                every { userManager.isUserLoggedIn() } returns true
                registrationViewModel.splashLiveData.observeForever(Observer {
                })
                registrationViewModel.splashLaunched()
                coroutineScope.advanceTimeBy(2000)
                Assert.assertEquals(
                    SplashEvent.DoSignIn,
                    registrationViewModel.splashLiveData.getOrAwaitValue().getValueIfNotHandled()
                )
            }
        }

    }


}


