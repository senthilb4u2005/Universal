package com.ps.universal.view.login

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.ps.universal.R
import com.ps.universal.view.dashboard.DashboardActivity
import com.ps.universal.viewmodel.SplashEvent
import com.ps.universal.viewmodel.LoginViewModel
import com.ps.universal.viewmodel.LoginViewModelFactory
import kotlinx.android.synthetic.main.fragment_splash.*


class SplashFragment : Fragment(R.layout.fragment_splash) {

    val viewModel: LoginViewModel by viewModels<LoginViewModel> {
        LoginViewModelFactory(context?.applicationContext as Application)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewModel.splashLaunched()
        viewModel.splashLiveData.observe(viewLifecycleOwner, Observer { it ->
            view.findNavController().popBackStack(R.id.splashFragment, true)


            it.getValueIfNotHandled()?.let { loginOptions ->
                when (loginOptions) {
                    SplashEvent.DoSignUp -> view.findNavController().navigate(R.id.signUpFragment)
                    SplashEvent.DoSignIn -> view.findNavController().navigate(R.id.signInFragment)
                }
            }

        })
        animateSplash()
    }



    private fun animateSplash() {
        val animation1 = AlphaAnimation(0.0f, 1.0f)
        animation1.duration = 5000
        animation1.fillAfter = true
        splashRoot.startAnimation(animation1)
    }


}