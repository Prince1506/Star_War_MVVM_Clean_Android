package com.mvvm_clean.user_details.features.people_list.presentation.models

import androidx.annotation.RestrictTo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mvvm_clean.user_details.core.base.BaseViewModel
import com.mvvm_clean.user_details.core.domain.exception.Failure
import com.mvvm_clean.user_details.core.domain.interactor.UseCase
import com.mvvm_clean.user_details.features.people_list.domain.models.PeopleListDataModel
import com.mvvm_clean.user_details.features.people_list.domain.use_cases.GetPeopleInfo
import javax.inject.Inject


open class PeopleListViewModel
@Inject constructor(private val getPeopleInfo: GetPeopleInfo) : BaseViewModel() {


    private val mPeopleListMutableLiveData: MutableLiveData<PeopleListDataModel> = MutableLiveData()
    private val mPeopleListLiveData: LiveData<PeopleListDataModel> = mPeopleListMutableLiveData


    internal fun getPeopleListLiveData() = mPeopleListLiveData

    internal fun loadPeopleList() =
        getPeopleInfo(UseCase.None()) { it.fold(::handlePeopleListFailure, ::handlePeopleList) }


    @RestrictTo(RestrictTo.Scope.TESTS)
    internal fun handlePeopleListFailure(failure: Failure?) {
        failure?.let { super.handleFailure(it) }
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    internal fun handlePeopleList(peopleListDataModel: PeopleListDataModel) {
        mPeopleListMutableLiveData.value = peopleListDataModel
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    public override fun onCleared() {
        super.onCleared()
    }

}