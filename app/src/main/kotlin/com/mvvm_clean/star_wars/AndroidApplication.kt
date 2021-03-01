package com.mvvm_clean.star_wars

import android.app.Application
import com.mvvm_clean.star_wars.core.di.ApplicationComponent
import com.mvvm_clean.star_wars.core.di.ApplicationModule
import com.mvvm_clean.star_wars.core.di.DaggerApplicationComponent

class AndroidApplication : Application() {

    val mAppComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        injectMembers()
    }

    private fun injectMembers() = mAppComponent.inject(this)
}
