package com.mvvm_clean.star_wars.features.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mvvm_clean.star_wars.core.exception.Failure
import com.mvvm_clean.star_wars.core.interactor.UseCase.None
import com.mvvm_clean.star_wars.core.platform.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PeopleListViewModel
@Inject constructor(private val getPeopleInfo: GetPeopleInfo) : BaseViewModel() {

    private val peopleListMutableLiveData: MutableLiveData<PeoplseListView> = MutableLiveData()
    val peopleListLiveData: LiveData<PeoplseListView> = peopleListMutableLiveData
    private val isProgressLoading = MutableLiveData<Boolean>()

    fun loadPeopleList() = getPeopleInfo(None()) { it.fold(::handlePeopleListFailure, ::handlePeopleList) }

    fun getIsLoading(): LiveData<Boolean?>? {
        return isProgressLoading
    }

    private fun handlePeopleListFailure(failure: Failure) {
        super.handleFailure(failure)
        isProgressLoading.value = false
    }

    private fun handlePeopleList(peopleListDataModel: PeopleListDataModel) {
        isProgressLoading.value = true
        peopleListMutableLiveData.value =  PeoplseListView(
            peopleListDataModel.count,
            peopleListDataModel.next,
            peopleListDataModel.previous,
            peopleListDataModel.result
        )
    }
}