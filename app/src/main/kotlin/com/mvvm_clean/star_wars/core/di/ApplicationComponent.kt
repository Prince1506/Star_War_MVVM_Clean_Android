package com.mvvm_clean.star_wars.core.di

import com.mvvm_clean.star_wars.AndroidApplication
import com.mvvm_clean.star_wars.core.presentation.viewmodel.ViewModelModule
import com.mvvm_clean.star_wars.core.presentation.navigation.RouteActivity
import com.mvvm_clean.star_wars.features.people_details.presentation.fragments.PeopleDetailsFragment
import com.mvvm_clean.star_wars.features.people_list.presentation.ui.fragments.PeopleListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: AndroidApplication)
    fun inject(routeActivity: RouteActivity)

    fun inject(peopleListFragment: PeopleListFragment)
    fun inject(peopleDetailsFragment: PeopleDetailsFragment)
}
