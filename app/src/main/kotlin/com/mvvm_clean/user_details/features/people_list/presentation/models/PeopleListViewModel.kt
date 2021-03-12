package com.mvvm_clean.user_details.features.people_list.presentation.models

import com.mvvm_clean.user_details.core.base.BaseViewModel
import com.mvvm_clean.user_details.features.people_list.domain.use_cases.GetPeopleInfo
import javax.inject.Inject


open class PeopleListViewModel
@Inject constructor(private val getPeopleInfo: GetPeopleInfo) : BaseViewModel() {
//
//
//    private val mPeopleListMutableLiveData: MutableLiveData<PeopleListView> = MutableLiveData()
//    private val mPeopleListLiveData: LiveData<PeopleListView> = mPeopleListMutableLiveData
//    private val mPeopleNameMutableLiveData = MutableLiveData<String>()
//    private val mIsProgressLoading = MutableLiveData<Boolean>()
//
//    private val peopleNameSearchObserver = Observer<String> {
//
//        mIsProgressLoading.value = true
//        getPeopleInfo(it) { it.fold(::handlePeopleListFailure, ::handlePeopleList) }
//    }
//
//    internal fun getPeopleListLiveData() = mPeopleListLiveData
//
//    @RestrictTo(RestrictTo.Scope.TESTS)
//    internal fun getProgressLoadingMutableLiveData() = mIsProgressLoading
//
//    @RestrictTo(RestrictTo.Scope.TESTS)
//    internal fun getPeopleNameMutabeLiveData() = mPeopleNameMutableLiveData
//
//    internal fun setSearchQueryString(userId: String) =
//
//        mPeopleNameMutableLiveData.apply {
//
//            postValue(userId)
//            observeForever(peopleNameSearchObserver)
//        }
//
//    internal fun loadPeopleList(searchQuery: String) =
//        getPeopleInfo(searchQuery) { it.fold(::handlePeopleListFailure, ::handlePeopleList) }
//
//    internal fun getProgressLoadingLiveData() = mIsProgressLoading
//
//    @RestrictTo(RestrictTo.Scope.TESTS)
//    internal fun handlePeopleListFailure(failure: Failure?) {
//        failure?.let { super.handleFailure(it) }
//        mIsProgressLoading.value = false
//    }
//
//    @RestrictTo(RestrictTo.Scope.TESTS)
//    internal fun handlePeopleList(peopleListDataModel: PeopleListDataModel) {
//        mIsProgressLoading.value = false
//
//        peopleListDataModel.apply {
//
//            mPeopleListMutableLiveData.value =
//                PeopleListView(count, next, previous, people)
//        }
//
//    }
//
//    @RestrictTo(RestrictTo.Scope.TESTS)
//    public override fun onCleared() {
//        mPeopleNameMutableLiveData.removeObserver(peopleNameSearchObserver)
//        super.onCleared()
//    }

}