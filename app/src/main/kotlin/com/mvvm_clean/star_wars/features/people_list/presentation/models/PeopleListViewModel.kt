package com.mvvm_clean.star_wars.features.people_list.presentation.models

import androidx.annotation.RestrictTo
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
    @RestrictTo(RestrictTo.Scope.TESTS)
    val peopleListMutableLiveData: MutableLiveData<PeopleListDataModel> = MutableLiveData()
    val peopleListLiveData: LiveData<PeopleListDataModel> = peopleListMutableLiveData


    open val peopleNameMutableLiveData = MutableLiveData<String>()

    open var isProgressLoading = MutableLiveData<Boolean>()
    fun getProgressLoadingMutableData() = isProgressLoading

    @RestrictTo(RestrictTo.Scope.TESTS)
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

    fun handlePeopleListFailure(failure: Failure?) {
        isProgressLoading.postValue(false)
        failure?.let { super.handleFailure(it) }
    }

    fun handlePeopleList(peopleListDataModel: PeopleListDataModel) {
        isProgressLoading.value = false
        peopleListMutableLiveData.postValue(peopleListDataModel)
    }

    public override fun onCleared() {
        peopleNameMutableLiveData.removeObserver(observer)
        super.onCleared()
    }


}