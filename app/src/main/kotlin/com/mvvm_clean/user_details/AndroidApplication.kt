package com.mvvm_clean.user_details

import android.app.Application
import com.mvvm_clean.user_details.core.di.ApplicationComponent
import com.mvvm_clean.user_details.core.di.ApplicationModule
import com.mvvm_clean.user_details.core.di.DaggerApplicationComponent

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
