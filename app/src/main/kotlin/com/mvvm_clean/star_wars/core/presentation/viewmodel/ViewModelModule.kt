package com.mvvm_clean.star_wars.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mvvm_clean.star_wars.features.people_details.presentation.models.PeopleDetailsViewModel
import com.mvvm_clean.star_wars.features.people_list.presentation.models.PeopleListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PeopleListViewModel::class)
    abstract fun bindsPeopleListViewModel(peopleListViewModel: PeopleListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PeopleDetailsViewModel::class)
    abstract fun bindsPeopleDetailsViewModel(peopleDetailsViewModel: PeopleDetailsViewModel): ViewModel
}