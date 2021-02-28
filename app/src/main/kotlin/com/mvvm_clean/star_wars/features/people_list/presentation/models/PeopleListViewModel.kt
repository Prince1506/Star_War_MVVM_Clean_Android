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


    private val peopleListMutableLiveData: MutableLiveData<PeoplseListView> = MutableLiveData()
    private val peopleListLiveData: LiveData<PeoplseListView> = peopleListMutableLiveData
    private val peopleNameMutableLiveData = MutableLiveData<String>()
    private val isProgressLoading = MutableLiveData<Boolean>()

    private val observer = Observer<String> {
        isProgressLoading.value = true
        getPeopleInfo(it) { it.fold(::handlePeopleListFailure, ::handlePeopleList) }
    }

    internal fun getPeopleListLiveData() = peopleListLiveData

    @RestrictTo(RestrictTo.Scope.TESTS)
    internal fun getProgressLoadingMutableLiveData() = isProgressLoading

    @RestrictTo(RestrictTo.Scope.TESTS)
    internal fun getPeopleNameMutabeLiveData() = peopleNameMutableLiveData

    internal fun setSearchQueryString(userId: String) {
        peopleNameMutableLiveData.postValue(userId)
        peopleNameMutableLiveData.observeForever(observer)
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    internal fun loadPeopleList(searchQuery: String) =
        getPeopleInfo(searchQuery) { it.fold(::handlePeopleListFailure, ::handlePeopleList) }

    @RestrictTo(RestrictTo.Scope.TESTS)
    internal fun getProgressLoadingLiveData(): LiveData<Boolean?>? {
        return isProgressLoading
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    internal fun handlePeopleListFailure(failure: Failure?) {
        failure?.let { super.handleFailure(it) }
        isProgressLoading.value = false
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    internal fun handlePeopleList(peopleListDataModel: PeopleListDataModel) {
        isProgressLoading.value = false
        peopleListMutableLiveData.value = PeoplseListView(
            peopleListDataModel.count,
            peopleListDataModel.next,
            peopleListDataModel.previous,
            peopleListDataModel.people
        )
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    public override fun onCleared() {
        peopleNameMutableLiveData.removeObserver(observer)
        super.onCleared()
    }

}