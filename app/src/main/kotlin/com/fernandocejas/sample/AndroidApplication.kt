package com.fernandocejas.sample

import android.app.Application
import com.fernandocejas.sample.core.di.ApplicationComponent
import com.fernandocejas.sample.core.di.ApplicationModule
import com.fernandocejas.sample.core.di.DaggerApplicationComponent

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
