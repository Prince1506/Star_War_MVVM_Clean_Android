package com.mvvm_clean.star_wars.core.di

import com.mvvm_clean.star_wars.AndroidApplication
import com.mvvm_clean.star_wars.core.di.viewmodel.ViewModelModule
import com.mvvm_clean.star_wars.core.navigation.RouteActivity
import com.mvvm_clean.star_wars.features.characters.MovieDetailsFragment
import com.mvvm_clean.star_wars.features.characters.PeopleListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: AndroidApplication)
    fun inject(routeActivity: RouteActivity)

    fun inject(peopleListFragment: PeopleListFragment)
    fun inject(movieDetailsFragment: MovieDetailsFragment)
}
