package com.mvvm_clean.star_wars.features.people_details.presentation.models

import androidx.annotation.RestrictTo
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

    private val mPeopleDetailsDataModel = PeopleDetailsDataModel()

    val mPeopleDetailMediatorLiveData = MediatorLiveData<PeopleDetailsDataModel>()
    private val speciesMutableLiveData: MutableLiveData<PeopleDetailsDataModel> = MutableLiveData()
    private val filmDataMutableLiveData: MutableLiveData<PeopleDetailsDataModel> = MutableLiveData()
    private val planetsMutableLiveData: MutableLiveData<PeopleDetailsDataModel> =
        MutableLiveData()

    fun getSpeciesMutableLiveData() = speciesMutableLiveData
    fun getFilmDataMutableLiveData() = filmDataMutableLiveData
    fun getPlanetsMutableLiveData() = planetsMutableLiveData

    fun updatePeopleDetailWithPeopleInfo(peopleEntity: PeopleEntity) {
        mPeopleDetailsDataModel.name = peopleEntity.name
        mPeopleDetailsDataModel.birthYear = peopleEntity.birth_year
        mPeopleDetailsDataModel.height = peopleEntity.height
    }

    fun loadFilmData(filmId: Int) {
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

    @RestrictTo(RestrictTo.Scope.TESTS)
    internal fun handlePlanetsData(planetListDataModel: PlanetListDataModel) {
        val size = planetListDataModel.results?.size
        if (size != null && size >= 1) {
            mPeopleDetailsDataModel.population = planetListDataModel.results.get(0).population
        }
        planetsMutableLiveData.postValue(mPeopleDetailsDataModel)

        mPeopleDetailMediatorLiveData.addSource(planetsMutableLiveData) {
            mPeopleDetailMediatorLiveData.value = it
            if (!mPeopleDetailsDataModel.isEmpty()) {
                //Hide progress
            }
        }
    }

    fun handleFilmData(filmDataModel: FilmDataModel) {

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

    @RestrictTo(RestrictTo.Scope.TESTS)
    internal fun handleSpeciesData(speciesListDataModel: SpeciesDataModel) {

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

}