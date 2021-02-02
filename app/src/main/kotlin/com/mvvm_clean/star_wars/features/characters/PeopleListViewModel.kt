package com.mvvm_clean.star_wars.features.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mvvm_clean.star_wars.core.interactor.UseCase.None
import com.mvvm_clean.star_wars.core.platform.BaseViewModel
import javax.inject.Inject

class PeopleListViewModel
@Inject constructor(private val getPeopleInfo: GetPeopleInfo) : BaseViewModel() {

    private val peopleListMutableLiveData: MutableLiveData<PeoplseListView> = MutableLiveData()
    val peopleListLiveData: LiveData<PeoplseListView> = peopleListMutableLiveData

    fun loadPeopleList() = getPeopleInfo(None()) { it.fold(::handleFailure, ::handlePeopleList) }

    private fun handlePeopleList(peopleListDataModel: PeopleListDataModel) {
        peopleListMutableLiveData.value =  PeoplseListView(
            peopleListDataModel.count,
            peopleListDataModel.next,
            peopleListDataModel.previous,
            peopleListDataModel.result
        )
    }
}