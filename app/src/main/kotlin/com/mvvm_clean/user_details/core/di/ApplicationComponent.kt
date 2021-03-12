package com.mvvm_clean.user_details.core.di

import com.mvvm_clean.user_details.AndroidApplication
import com.mvvm_clean.user_details.core.presentation.navigation.RouteActivity
import com.mvvm_clean.user_details.core.presentation.viewmodel.ViewModelModule
import com.mvvm_clean.user_details.features.login.presentation.fragments.LoginFragment
import dagger.Component
import javax.inject.Singleton

/**
 * DI component class responsible to provide instance.
 */
@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(application: AndroidApplication)
    fun inject(routeActivity: RouteActivity)

    //    fun inject(peopleListFragment: PeopleListFragment)
    fun inject(loginFragment: LoginFragment)
//    fun inject(peopleDetailsFragment: PeopleDetailsFragment)
}
