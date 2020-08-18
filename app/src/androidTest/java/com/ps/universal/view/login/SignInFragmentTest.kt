package com.ps.universal.view.login


import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.material.textfield.TextInputLayout
import com.ps.universal.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignInFragmentTest {

    @Test
    fun verify_ui_with_proper_hint() {

        val subject = launchFragmentInContainer<SignInFragment>(themeResId = R.style.AppTheme)

        onView(withId(R.id.txt_email)).check(
            matches(
                withHint(R.string.email)
            )
        )

        onView(withId(R.id.txt_password)).check(
            matches(
                withHint(R.string.passowrd)
            )
        )

        onView(withId(R.id.loginBtn)).check(matches(isDisplayed()))
    }


    @Test
    fun on_remove_me_click_navigate_to_sign_up_screen() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.app_nav)

        val scenario = launchFragmentInContainer(themeResId = R.style.AppTheme) {
            SignInFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        // The fragment’s view has just been created
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }
        onView(withId(R.id.deleteUser)).perform(click())
        assertEquals(R.id.signUpFragment, navController.currentDestination?.id)
    }

    @Test
    fun on_not_a_member_click_navigate_to_sign_up_screen() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.app_nav)

        val scenario = launchFragmentInContainer(themeResId = R.style.AppTheme) {
            SignInFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        // The fragment’s view has just been created
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }
        onView(withId(R.id.createAccount)).perform(click())
        assertEquals(R.id.signUpFragment, navController.currentDestination?.id)
    }

    @Test
    fun sign_in_with_invalid_credentials_show_error_message() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.app_nav)

        val scenario = launchFragmentInContainer(themeResId = R.style.AppTheme) {
            SignInFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        // The fragment’s view has just been created
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }
        onView(withId(R.id.txt_email)).perform(typeText("senthil@gmail.com"))
        onView(withId(R.id.txt_password)).perform(typeText("password"))
        onView(withId(R.id.loginBtn)).perform(click())

        val string =
            InstrumentationRegistry.getInstrumentation()?.context?.getString(R.string.login_error)
                ?: ""
        onView(withId(R.id.txt_password)).check(
            matches(
                hasTextInputLayoutErrorText(string)
            )
        )


    }
}

fun hasTextInputLayoutErrorText(expectedErrorText: String): Matcher<View?>? {
    return object : TypeSafeMatcher<View?>() {
        override fun matchesSafely(view: View?): Boolean {
            if (view !is TextInputLayout) {
                return false
            }
            val error = view.error ?: return false
            val hint = error.toString()
            return expectedErrorText == hint
        }

        override fun describeTo(description: Description) {}
    }
}