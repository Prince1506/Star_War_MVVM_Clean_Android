package com.fernandocejas.sample.core.di

import com.fernandocejas.sample.AndroidApplication
import com.fernandocejas.sample.core.di.viewmodel.ViewModelModule
import com.fernandocejas.sample.core.navigation.RouteActivity
import com.fernandocejas.sample.features.movies.MovieDetailsFragment
import com.fernandocejas.sample.features.movies.MoviesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: AndroidApplication)
    fun inject(routeActivity: RouteActivity)

    fun inject(moviesFragment: MoviesFragment)
    fun inject(movieDetailsFragment: MovieDetailsFragment)
}
