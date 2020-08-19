package com.ps.universal.view.login

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ps.universal.R
import com.ps.universal.view.registration.SignUpFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SignUpFragmentTest {

    @Test
    fun verifyFieldHasProperHint() {

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.app_nav)

        val fragmentScenario = launchFragmentInContainer<SignUpFragment>()
        fragmentScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        onView(withId(R.id.full_name)).check(matches(withHint(R.string.fullName)))
        onView(withId(R.id.userEmailId)).check(matches(withHint(R.string.email)))
        onView(withId(R.id.mobileNumber)).check(matches(withHint(R.string.mobileNumber)))
        onView(withId(R.id.location)).check(matches(withHint(R.string.location)))
        onView(withId(R.id.password)).check(matches(withHint(R.string.passowrd)))
        onView(withId(R.id.confirmPassword)).check(matches(withHint(R.string.confirmPassword)))
        onView(withId(R.id.already_user)).perform(ViewActions.click())
        assert(R.id.signInFragment == navController.currentDestination?.id)
    }


    @Test
    fun alreadySignedInLaunchSignInFragment() {


        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.app_nav)

        val fragmentScenario = launchFragmentInContainer<SignUpFragment>()
        fragmentScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        onView(withId(R.id.already_user)).perform(ViewActions.click())
        assert(R.id.signInFragment == navController.currentDestination?.id)
    }
}