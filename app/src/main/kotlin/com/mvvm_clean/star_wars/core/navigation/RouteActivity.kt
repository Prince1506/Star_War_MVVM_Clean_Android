package com.mvvm_clean.star_wars.core.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mvvm_clean.star_wars.AndroidApplication
import com.mvvm_clean.star_wars.core.di.ApplicationComponent
import javax.inject.Inject

class RouteActivity : AppCompatActivity() {

    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as AndroidApplication).appComponent
    }

    @Inject
    internal lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        navigator.showMain(this)
    }
}
