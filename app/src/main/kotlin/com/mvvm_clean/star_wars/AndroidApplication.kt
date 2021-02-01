package com.mvvm_clean.star_wars

import android.app.Application
import com.mvvm_clean.star_wars.core.di.ApplicationComponent
import com.mvvm_clean.star_wars.core.di.ApplicationModule
import com.mvvm_clean.star_wars.core.di.DaggerApplicationComponent

class AndroidApplication : Application() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
    }

    private fun injectMembers() = appComponent.inject(this)
}
