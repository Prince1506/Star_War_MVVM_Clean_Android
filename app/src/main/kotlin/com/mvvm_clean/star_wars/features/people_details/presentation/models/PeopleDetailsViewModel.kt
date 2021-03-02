package com.mvvm_clean.star_wars.features.people_details.presentation.models

import androidx.annotation.RestrictTo
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mvvm_clean.star_wars.core.base.BaseViewModel
import com.mvvm_clean.star_wars.core.domain.extension.putNewLine
import com.mvvm_clean.star_wars.features.people_details.domain.models.FilmDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.PeopleDetailsDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.PlanetListDataModel
import com.mvvm_clean.star_wars.features.people_details.domain.models.SpeciesDataModel
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

    // Field Variables-----------------------------------------------
    private val peopleDetailsDataModel = PeopleDetailsDataModel()
    private val peopleDetailMediatorLiveData = MediatorLiveData<PeopleDetailsDataModel>()
    private val speciesMutableLiveData: MutableLiveData<PeopleDetailsDataModel> = MutableLiveData()
    private val filmDataMutableLiveData: MutableLiveData<PeopleDetailsDataModel> =
        MutableLiveData()
    private val planetsMutableLiveData: MutableLiveData<PeopleDetailsDataModel> =
        MutableLiveData()

    //  Helper Methods-----------------------------------------
    fun getSpeciesMutableLiveData() = speciesMutableLiveData
    fun getFilmDataMutableLiveData() = filmDataMutableLiveData
    fun getPlanetsMutableLiveData() = planetsMutableLiveData
    internal fun getPeopleDetailMediatorLiveData() = peopleDetailMediatorLiveData

    fun updatePeopleDetailWithPeopleInfo(peopleEntity: PeopleEntity) =
        peopleDetailsDataModel.apply {
            name = peopleEntity.name
            birthYear = peopleEntity.birth_year
            height = peopleEntity.height
        }


    fun loadFilmData(filmId: Int) =
        getFilmNames(filmId) { it.fold(::handleFailure, ::handleFilmData) }

    fun loadSpeciesData(speciesId: Int) =
        getSpeciesInfo(speciesId) { it.fold(::handleFailure, ::handleSpeciesData) }

    fun loadPlanetData(planetId: Int) =
        getPlanetsInfo(planetId) { it.fold(::handleFailure, ::handlePlanetsData) }

    @RestrictTo(RestrictTo.Scope.TESTS)
    internal fun handlePlanetsData(planetListDataModel: PlanetListDataModel) {
        peopleDetailsDataModel.apply {
            homeworld = planetListDataModel.name
            population = planetListDataModel.population
            planetsMutableLiveData.postValue(this)
        }

        peopleDetailMediatorLiveData.addSource(planetsMutableLiveData) {
            peopleDetailMediatorLiveData.value = it
        }
    }

    fun handleFilmData(filmDataModel: FilmDataModel) {

        updatePeopleDetailWithFilmData(filmDataModel)
        addFilmLiveDataToPeopleMediatorLiveData()
    }

    private fun addFilmLiveDataToPeopleMediatorLiveData() {
        peopleDetailMediatorLiveData.apply {
            addSource(filmDataMutableLiveData) {
                value = it
            }
            removeSource(filmDataMutableLiveData)
        }
    }

    private fun updatePeopleDetailWithFilmData(filmDataModel: FilmDataModel) {

        peopleDetailsDataModel.let {
            if (it.film?.isEmpty() == false) {
                it.film += filmDataModel.title + String.putNewLine()
            } else {
                it.film = filmDataModel.title
            }
            it.openingCrawl += filmDataModel.title +
                    String.putNewLine() +
                    filmDataModel.opening_crawl +
                    String.putNewLine() +
                    String.putNewLine()

            filmDataMutableLiveData.postValue(it)
        }
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    internal fun handleSpeciesData(speciesListDataModel: SpeciesDataModel) {

        updatePeopleDataWithSpeciesData(speciesListDataModel)
        addSpeciesLiveDataToPeopleMediatorLiveData()
    }

    private fun addSpeciesLiveDataToPeopleMediatorLiveData() {
        peopleDetailMediatorLiveData.apply {
            addSource(speciesMutableLiveData) {
                value = it
            }
            removeSource(speciesMutableLiveData)
        }
    }

    private fun updatePeopleDataWithSpeciesData(speciesListDataModel: SpeciesDataModel) {
        peopleDetailsDataModel.let {
            it.speciesName += speciesListDataModel.name + String.putNewLine()
            it.languages += speciesListDataModel.language + String.putNewLine()
            speciesMutableLiveData.postValue(it)
        }
    }

}
