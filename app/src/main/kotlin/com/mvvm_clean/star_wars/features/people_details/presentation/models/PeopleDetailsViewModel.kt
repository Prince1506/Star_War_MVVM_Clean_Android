package com.mvvm_clean.star_wars.features.people_details.presentation.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mvvm_clean.star_wars.core.base.BaseViewModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.PeopleDetailsDataModel
import javax.inject.Inject

class PeopleDetailsViewModel
@Inject constructor(
//    private val getPeopleDetails: GetPeopleDetails,
) : BaseViewModel() {

    private val _peopleDetailsMutableLiveData: MutableLiveData<PeopleDetailsView> = MutableLiveData()
    val peopleDetailsLiveData: LiveData<PeopleDetailsView> = _peopleDetailsMutableLiveData

    fun loadPeopleDetails(movieId: String) {
//        getPeopleDetails(Params(movieId)) { it.fold(::handleFailure, ::handlePeopleDetails) }

    }



    private fun handlePeopleDetails(peopleDataModel: PeopleDetailsDataModel) {
//        _peopleDetailsMutableLiveData.value = PeopleDetailsView(
//            peopleDataModel.id, peopleDataModel.title, peopleDataModel.poster,
//            peopleDataModel.summary, peopleDataModel.cast, peopleDataModel.director, peopleDataModel.year, peopleDataModel.trailer
//        )
    }
}