package com.mvvm_clean.star_wars.features.people_list.presentation.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mvvm_clean.star_wars.core.base.BaseViewModel
import com.mvvm_clean.star_wars.core.domain.exception.Failure
import com.mvvm_clean.star_wars.features.people_list.domain.models.PeopleListDataModel
import com.mvvm_clean.star_wars.features.people_list.domain.use_cases.GetPeopleInfo
import javax.inject.Inject


open class PeopleListViewModel
@Inject constructor(private val getPeopleInfo: GetPeopleInfo) : BaseViewModel() {


    private val peopleListMutableLiveData: MutableLiveData<PeoplseListView> = MutableLiveData()
    val peopleListLiveData: LiveData<PeoplseListView> = peopleListMutableLiveData
    val peopleNameMutableLiveData = MutableLiveData<String>()

    open fun getPeopleNameMutabeLiveData() = peopleNameMutableLiveData
    val isProgressLoading = MutableLiveData<Boolean>()
    open fun getProgressLoading() = isProgressLoading
    val observer = Observer<String> {
        isProgressLoading.value = true
        getPeopleInfo(it) { it.fold(::handlePeopleListFailure, ::handlePeopleList) }
    }


    fun setSearchQueryString(userId: String) {
        peopleNameMutableLiveData.postValue(userId)
        peopleNameMutableLiveData.observeForever(observer)
    }

    fun loadPeopleList(searchQuery: String) =
        getPeopleInfo(searchQuery) { it.fold(::handlePeopleListFailure, ::handlePeopleList) }

    fun getIsLoading(): LiveData<Boolean?>? {
        return isProgressLoading
    }

    open fun handlePeopleListFailure(failure: Failure?) {
        failure?.let { super.handleFailure(it) }
        isProgressLoading.value = false
    }

    open fun handlePeopleList(peopleListDataModel: PeopleListDataModel) {
        isProgressLoading.value = false
        peopleListMutableLiveData.value = PeoplseListView(
            peopleListDataModel.count,
            peopleListDataModel.next,
            peopleListDataModel.previous,
            peopleListDataModel.people
        )
    }

    public override fun onCleared() {
        peopleNameMutableLiveData.removeObserver(observer)
        super.onCleared()
    }
}