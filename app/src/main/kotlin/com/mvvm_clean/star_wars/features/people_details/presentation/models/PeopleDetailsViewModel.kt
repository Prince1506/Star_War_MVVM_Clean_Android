package com.mvvm_clean.star_wars.features.people_details.presentation.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mvvm_clean.star_wars.core.base.BaseViewModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.PeopleDetailsDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.PlanetListDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.SpeciesListDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.use_cases.GetPlanetsInfo
import com.mvvm_clean.star_wars.features.people_details.domain.use_cases.GetSpeciesInfo
import javax.inject.Inject

class PeopleDetailsViewModel
@Inject constructor(
    private val getSpeciesInfo: GetSpeciesInfo,
    private val getPlanetsInfo: GetPlanetsInfo,
) : BaseViewModel() {

    val mPeopleDetailMediatorLiveData = MediatorLiveData<PeopleDetailsDataModel>()
    private val mPeopleDetailsDataModel = PeopleDetailsDataModel()

    private val speciesMutableLiveData: MutableLiveData<PeopleDetailsDataModel> = MutableLiveData()
    private val speciesDetailsLiveData: LiveData<PeopleDetailsDataModel> = speciesMutableLiveData

    private val planetsDetailsMutableLiveData: MutableLiveData<PeopleDetailsDataModel> =
        MutableLiveData()

    private val planetsDetailsLiveData: LiveData<PeopleDetailsDataModel> =
        planetsDetailsMutableLiveData

    fun loadSpeciesData(searchQuery: String) {
        getSpeciesInfo(searchQuery) {
            it.fold(::handleFailure, ::handleSpeciesData)
        }
    }

    fun loadPlanetData(searchQuery: String) {
        getPlanetsInfo(searchQuery) {
            it.fold(::handleFailure, ::handlePlanetsData)
        }
    }

    private fun handlePlanetsData(planetListDataModel: PlanetListDataModel) {
        val size = planetListDataModel.results?.size
        if (size != null && size >= 1) {
            mPeopleDetailsDataModel.population = planetListDataModel.results.get(0).population
        }
        planetsDetailsMutableLiveData.postValue(mPeopleDetailsDataModel)

        mPeopleDetailMediatorLiveData.addSource(planetsDetailsMutableLiveData) {
            mPeopleDetailMediatorLiveData.value = it
            if (!mPeopleDetailsDataModel.isEmpty()) {
                //Hide progress
            }
        }

    }


    private fun handleSpeciesData(speciesListDataModel: SpeciesListDataModel) {

        val size = speciesListDataModel.results?.size
        if (size != null && size >= 1) {
            mPeopleDetailsDataModel.homeworld = speciesListDataModel.results?.get(0)?.homeworld
            mPeopleDetailsDataModel.name = speciesListDataModel.results?.get(0)?.name
            mPeopleDetailsDataModel.languages = speciesListDataModel.results?.get(0)?.language
        }
        speciesMutableLiveData.postValue(mPeopleDetailsDataModel)

        mPeopleDetailMediatorLiveData.addSource(speciesMutableLiveData) {
            mPeopleDetailMediatorLiveData.value = it
            if (!mPeopleDetailsDataModel.isEmpty()) {
                //Hide progress
            }
        }
    }


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