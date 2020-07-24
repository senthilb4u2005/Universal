package com.ps.universal.view.login

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ps.universal.R
import com.ps.universal.view.dashboard.DashboardActivity
import com.ps.universal.viewmodel.LoginViewModel
import com.ps.universal.viewmodel.LoginViewModelFactory
import com.ps.universal.viewmodel.SignInEvent
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    val viewModel: LoginViewModel by viewModels<LoginViewModel> {
        LoginViewModelFactory(requireContext().applicationContext as Application)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createAccount.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }

        loginBtn.setOnClickListener {
            if (isValid(txt_email.text.toString(), txt_password.text.toString())) {
                viewModel.doSignIn(txt_email.text.toString(), txt_password.text.toString())
            } else {
                Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.signInLiveData.observe(viewLifecycleOwner, Observer { it ->
            it.getValueIfNotHandled().let { signInEvent ->
                when (signInEvent) {
                    is SignInEvent.SignInFailed -> {
                        Toast.makeText(requireContext(), signInEvent.error, Toast.LENGTH_SHORT)
                            .show()

                    }
                    is SignInEvent.SignInSuccess -> {

                        launchDashboard()
                    }

                    is SignInEvent.BiometricAuthentication -> {
                        doBiometricAuthentication()
                    }
                }
            }
        })

        deleteUser.setOnClickListener {
            viewModel.removeRegisteredUser()
            findNavController().popBackStack(R.id.signInFragment, true)
            findNavController().navigate(R.id.signUpFragment)

        }

        viewModel.checkBiometricAuthentication()
    }

    private fun isValid(email: String, password: String): Boolean =
        email.isNotEmpty() && password.isNotEmpty()

    private fun doBiometricAuthentication() {
        val executor = ContextCompat.getMainExecutor(requireContext())

        var biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        requireContext(),
                        "Authentication error: $errString", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    launchDashboard()

                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        requireContext(), "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })

        var promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Check")
            .setConfirmationRequired(false)
            .build()
        biometricPrompt.authenticate(promptInfo)

    }

    private fun launchDashboard() {
        requireActivity().startActivity(
            Intent(
                requireActivity(),
                DashboardActivity::class.java
            )
        )
        requireActivity().finish()
    }

}