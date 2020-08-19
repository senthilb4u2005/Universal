package com.ps.universal.view.splash

import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ps.universal.R
import com.ps.universal.UniversalApplication
import com.ps.universal.usermanager.UserManager
import com.ps.universal.view.dashboard.ui.DashboardActivity
import com.ps.universal.view.registration.RegistrationActivity
import com.ps.universal.viewmodel.SplashEvent
import com.ps.universal.viewmodel.SplashViewModel
import com.ps.universal.viewmodel.SplashViewModelFactory
import kotlinx.android.synthetic.main.fragment_splash.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var userManager: UserManager

    private val viewModel: SplashViewModel by viewModels<SplashViewModel> {
        SplashViewModelFactory(userManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as UniversalApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)
        viewModel.splashLiveData.observe(this, Observer {
            it.getValueIfNotHandled().let { splashEvent ->
                when (splashEvent) {
                    is SplashEvent.DoSignIn -> launchDashboardActivity()
                    is SplashEvent.DoSignUp -> launchRegistrationActivity()
                }
            }

        })
        viewModel.splashLaunched()
    }

    override fun onResume() {
        super.onResume()
        animateSplash()
    }

    private fun launchRegistrationActivity() {
        startActivity(
            Intent(
                this,
                RegistrationActivity::class.java
            )
        )
        finish()
    }

    private fun launchDashboardActivity() {
        startActivity(
            Intent(
                this,
                DashboardActivity::class.java
            )
        )
        finish()

    }

    private fun animateSplash() {
        val animation1 = AlphaAnimation(0.0f, 1.0f)
        animation1.duration = 2000
        animation1.fillAfter = true
        splashRoot.startAnimation(animation1)
    }

}