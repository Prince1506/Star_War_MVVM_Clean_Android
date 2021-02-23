package com.mvvm_clean.star_wars.features.people_list.presentation.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mvvm_clean.star_wars.core.base.BaseViewModel
import com.mvvm_clean.star_wars.core.domain.exception.Failure
import com.mvvm_clean.star_wars.features.people_list.domain.models.PeopleListDataModel
import com.mvvm_clean.star_wars.features.people_list.domain.use_cases.GetPeopleInfo
import javax.inject.Inject


class PeopleListViewModel
@Inject constructor(private val getPeopleInfo: GetPeopleInfo) : BaseViewModel() {


    private val peopleListMutableLiveData: MutableLiveData<PeoplseListView> = MutableLiveData()
    val peopleListLiveData: LiveData<PeoplseListView> = peopleListMutableLiveData
    private val peopleNameMutableLiveData = MutableLiveData<String>()

    private val isProgressLoading = MutableLiveData<Boolean>()

    val observer = Observer<String> {
        isProgressLoading.value = true
        getPeopleInfo(it) { it.fold(::handlePeopleListFailure, ::handlePeopleList) }
    }


    fun setSearchQueryString(userId: String) {
        this.peopleNameMutableLiveData.postValue(userId)
        peopleNameMutableLiveData.observeForever(observer)
    }

    fun loadPeopleList(searchQuery: String) =
        getPeopleInfo(searchQuery) { it.fold(::handlePeopleListFailure, ::handlePeopleList) }

    fun getIsLoading(): LiveData<Boolean?>? {
        return isProgressLoading
    }

    private fun handlePeopleListFailure(failure: Failure) {
        super.handleFailure(failure)
        isProgressLoading.value = false
    }

    private fun handlePeopleList(peopleListDataModel: PeopleListDataModel) {
        isProgressLoading.value = false
        peopleListMutableLiveData.value = PeoplseListView(
            peopleListDataModel.count,
            peopleListDataModel.next,
            peopleListDataModel.previous,
            peopleListDataModel.people
        )
    }

    override fun onCleared() {
        peopleNameMutableLiveData.removeObserver(observer)
        super.onCleared()
    }
}