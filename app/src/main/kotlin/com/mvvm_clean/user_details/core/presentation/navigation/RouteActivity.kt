package com.mvvm_clean.user_details.core.presentation.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mvvm_clean.user_details.AndroidApplication
import com.mvvm_clean.user_details.core.di.ApplicationComponent
import javax.inject.Inject

/**
 * This is the launcher activity. It will initiate Navigation hence called
 * Routing Activity
 */
class RouteActivity : AppCompatActivity() {

    // Field Variables ---------------------
    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as AndroidApplication).mAppComponent
    }

    // Annotations Variables -----------------
    @Inject
    internal lateinit var navigator: Navigator

    //  Override Methods--------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        navigator.navigateToScreens(this)
    }
}
