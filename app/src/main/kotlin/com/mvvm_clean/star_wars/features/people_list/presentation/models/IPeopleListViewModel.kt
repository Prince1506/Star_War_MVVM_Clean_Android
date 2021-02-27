package com.mvvm_clean.star_wars.features.people_list.presentation.models

import androidx.lifecycle.MutableLiveData

interface IPeopleListViewModel {
    //    open fun getPeopleNameMutabeLiveData(): MutableLiveData<String>
    open fun getProgressLoading(): MutableLiveData<Boolean>
}
