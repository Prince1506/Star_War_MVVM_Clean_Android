package com.mvvm_clean.star_wars.features.people_details.presentation.models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mvvm_clean.star_wars.core.base.BaseViewModel
import com.mvvm_clean.star_wars.features.people_details.data.repo.response.SpeciesDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.FilmDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.PeopleDetailsDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.PlanetListDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.use_cases.GetFilmNames
import com.mvvm_clean.star_wars.features.people_details.domain.use_cases.GetPlanetsInfo
import com.mvvm_clean.star_wars.features.people_details.domain.use_cases.GetSpeciesInfo
import com.mvvm_clean.star_wars.features.people_list.data.repo.response.PeopleEntity
import javax.inject.Inject

class PeopleDetailsViewModel
@Inject constructor(
    private val getSpeciesInfo: GetSpeciesInfo,
    private val getPlanetsInfo: GetPlanetsInfo,
    private val getFilmNames: GetFilmNames
) : BaseViewModel() {

    val mPeopleDetailMediatorLiveData = MediatorLiveData<PeopleDetailsDataModel>()

    private val mPeopleDetailsDataModel = PeopleDetailsDataModel()

    private val speciesMutableLiveData: MutableLiveData<PeopleDetailsDataModel> = MutableLiveData()

    private val planetsDetailsMutableLiveData: MutableLiveData<PeopleDetailsDataModel> =
        MutableLiveData()

    private val filmDataMutableLiveData: MutableLiveData<PeopleDetailsDataModel> = MutableLiveData()

    fun updatePeopleDetailWithPeopleInfo(peopleEntity: PeopleEntity) {
        mPeopleDetailsDataModel.name = peopleEntity.name
        mPeopleDetailsDataModel.birthYear = peopleEntity.birth_year
        mPeopleDetailsDataModel.height = peopleEntity.height
    }

    fun getFilmsFromId(filmId: Int) {
        getFilmNames(filmId) {
            it.fold(::handleFailure, ::handleFilmData)
        }
    }


    fun loadSpeciesData(speciesId: Int) {
        getSpeciesInfo(speciesId) {
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


    private fun handleFilmData(filmDataModel: FilmDataModel) {

        mPeopleDetailsDataModel.let {
            if (it.film?.isEmpty() == false) {
                mPeopleDetailsDataModel.film += filmDataModel.title + " <br> "
            } else {
                mPeopleDetailsDataModel.film = filmDataModel.title
            }
            it.openingCrawl += filmDataModel.title + " : <br> " + filmDataModel.opening_crawl + " <br> <br> "
            filmDataMutableLiveData.postValue(it)
        }

        mPeopleDetailMediatorLiveData.addSource(filmDataMutableLiveData) {
            mPeopleDetailMediatorLiveData.value = it
            if (!mPeopleDetailsDataModel.isEmpty()) {
                //Hide progress
            }
        }
        mPeopleDetailMediatorLiveData.removeSource(filmDataMutableLiveData)
    }

    private fun handleSpeciesData(speciesListDataModel: SpeciesDataModel) {

        mPeopleDetailsDataModel.let {
            it.homeworld += speciesListDataModel.homeworld + " <br> "
            it.speciesName += speciesListDataModel.name + " <br> "
            it.languages += speciesListDataModel.language + " <br> "
            speciesMutableLiveData.postValue(it)
        }

        mPeopleDetailMediatorLiveData.addSource(speciesMutableLiveData) {
            mPeopleDetailMediatorLiveData.value = it
            if (!mPeopleDetailsDataModel.isEmpty()) {
                //Hide progress
            }
        }
        mPeopleDetailMediatorLiveData.removeSource(speciesMutableLiveData)
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