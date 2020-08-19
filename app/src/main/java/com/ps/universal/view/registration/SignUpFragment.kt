package com.ps.universal.view.registration

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ps.universal.R
import com.ps.universal.view.dashboard.ui.DashboardActivity
import com.ps.universal.viewmodel.RegistrationViewModel
import com.ps.universal.viewmodel.RegistrationViewModelFactory
import com.ps.universal.viewmodel.SignUpEvent
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.util.regex.Pattern


private val MOBILE_NUMBER_MATCHER: Pattern =
    Pattern.compile("^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}\$")
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {



    private val viewModel by viewModels<RegistrationViewModel> {
        RegistrationViewModelFactory(requireContext().applicationContext as Application)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        already_user.setOnClickListener {
            requireActivity().startActivity(
                Intent(
                    requireActivity(),
                    DashboardActivity::class.java
                )
            )
            requireActivity().finish()
        }

        viewModel.signUpLiveData.observe(viewLifecycleOwner, Observer {
            it.getValueIfNotHandled()?.let { options ->
                when (options) {
                    is SignUpEvent.SignUpFailed -> {
                        Toast.makeText(requireContext(), options.error, Toast.LENGTH_SHORT).show()
                    }
                    is SignUpEvent.SignUpSuccess -> {

                        findNavController().popBackStack(R.id.signUpFragment, true)
                        findNavController().navigate(R.id.termsAndConditionFragment)

                    }

                }
            }
        })

        signUpBtn.setOnClickListener {

            if (isValidFields()) {
                viewModel.doSignUp(
                    full_name.text.toString(),
                    userEmailId.text.toString(),
                    mobileNumber.text.toString(),
                    location.text.toString(),
                    password.text.toString(),
                    confirmPassword.text.toString()
                )
            }
        }
    }

    private fun isValidFields(): Boolean {

        if (full_name.text.toString().isEmpty()) {
            Toast.makeText(context, "Please enter full name", Toast.LENGTH_SHORT).show()
            return false
        }
        if (userEmailId.text.toString().isEmpty()) {
            Toast.makeText(context, "Please enter email", Toast.LENGTH_SHORT).show()
            return false
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(userEmailId.text.toString()).matches()) {
                Toast.makeText(context, "Please enter valid email", Toast.LENGTH_SHORT).show()
            }
        }
        if (mobileNumber.text.toString().isEmpty()) {
            Toast.makeText(context, "Please enter phone number", Toast.LENGTH_SHORT).show()
            return false
        } else {

            if (!MOBILE_NUMBER_MATCHER.matcher(mobileNumber.text.toString()).matches()) {
                Toast.makeText(context, "Please enter valid phone number", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        if (location.text.toString().isEmpty()) {
            Toast.makeText(context, "Please enter location name", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.text.toString().isEmpty() && password.text.toString().length < 8) {
            Toast.makeText(
                context,
                "Please enter password with minimum length of eight char long",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        if (confirmPassword.text.toString().isEmpty()) {
            Toast.makeText(
                context,
                "Please enter confirm password with minimum length of eight char long",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        if (password.text.toString() != confirmPassword.text.toString()) {
            Toast.makeText(
                context,
                "Password and Confirm password does not match",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }


        return true
    }
}