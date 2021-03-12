package com.mvvm_clean.user_details.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mvvm_clean.user_details.features.login.presentation.view_models.LoginViewModel
import com.mvvm_clean.user_details.features.people_details.presentation.models.PeopleDetailsViewModel
import com.mvvm_clean.user_details.features.people_list.presentation.models.PeopleListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Base View Module Factory class to provide specific view module instances
 */
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
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(peopleListViewModel: LoginViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(PeopleDetailsViewModel::class)
    abstract fun bindsPeopleDetailsViewModel(peopleDetailsViewModel: PeopleDetailsViewModel): ViewModel
}