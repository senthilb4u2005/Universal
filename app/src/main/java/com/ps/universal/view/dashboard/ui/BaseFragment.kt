package com.ps.universal.view.dashboard.ui

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ps.universal.R
import com.ps.universal.UniversalApplication

abstract class BaseFragment : Fragment() {

    abstract fun isAuthenticationRequired(): Boolean

    private fun checkAuthentication() {
        if (isAuthenticationRequired() &&  !(requireActivity().applicationContext as UniversalApplication).isSessionActive()) {
            findNavController().popBackStack()
            findNavController().navigate(R.id.signInFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        checkAuthentication()
    }

}