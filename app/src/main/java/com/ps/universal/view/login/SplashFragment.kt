package com.ps.universal.view.login

import android.app.Application
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.ps.universal.R
import com.ps.universal.viewmodel.SplashEvent
import com.ps.universal.viewmodel.RegistrationViewModel
import com.ps.universal.viewmodel.LoginViewModelFactory
import kotlinx.android.synthetic.main.fragment_splash.*


class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: RegistrationViewModel by viewModels<RegistrationViewModel> {
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
        animation1.duration = 2000
        animation1.fillAfter = true
        splashRoot.startAnimation(animation1)
    }


}