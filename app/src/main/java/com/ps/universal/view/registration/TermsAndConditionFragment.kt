package com.ps.universal.view.registration

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ps.universal.R
import com.ps.universal.view.dashboard.ui.DashboardActivity
import com.ps.universal.viewmodel.RegistrationViewModel
import com.ps.universal.viewmodel.RegistrationViewModelFactory
import com.ps.universal.viewmodel.TermsAndConditionEvent
import kotlinx.android.synthetic.main.fragment_terms_and_condition.*


class TermsAndConditionFragment : Fragment(R.layout.fragment_terms_and_condition) {

    private val viewModel by viewModels<RegistrationViewModel> {
        RegistrationViewModelFactory(requireContext().applicationContext as Application)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accept.setOnClickListener {
            viewModel.onTermsAndConditionAccept()
        }
        decline.setOnClickListener {
            viewModel.onTermsAndConditionDecline()
        }

        viewModel.termsAndConditionLiveData.observe(viewLifecycleOwner, Observer { it ->
            it.getValueIfNotHandled()?.let { termsAndConditionEvent ->
                when (termsAndConditionEvent) {
                    is TermsAndConditionEvent.Accept -> {
                        requireActivity().startActivity(
                            Intent(
                                requireActivity(),
                                DashboardActivity::class.java
                            )
                        )
                        requireActivity().finish()
                    }
                    is TermsAndConditionEvent.Decline -> {
                        requireActivity().finish()
                    }
                }
            }
        })
    }


}