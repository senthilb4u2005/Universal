package com.ps.universal

import android.app.Application
import com.ps.universal.dependency.DaggerAppComponent

class UniversalApplication : Application() {

    val appComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}